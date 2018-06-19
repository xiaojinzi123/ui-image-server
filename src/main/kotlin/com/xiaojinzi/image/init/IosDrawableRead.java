package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 实现 IOS 项目的资源的读取
 * {@link #readFolder(ArrayList, File)} 方法的参数,File 本身不应该是一个图片资源,应该是一个类别,或者类别的类别
 */
public class IosDrawableRead implements DrawableRead {

    @Override
    @NotNull
    public ProjectDrawable read(Project pro) {

        ProjectDrawable result = doReadList(pro);

        // 对内部的 drawable 做一个排序

        ArrayList<DrawableCategory> categories = result.getCategories();

        for (DrawableCategory category : categories) {

            ArrayList<Drawables> drawableList = category.getDrawablesList();

            for (Drawables drawables : drawableList) {

                // 对这个排序
                List<Drawable> drawablesList = drawables.getDrawables();

                drawablesList.sort(new Comparator<Drawable>() {
                    @Override
                    public int compare(Drawable o1, Drawable o2) {

                        String drawableName1 = o1.getDrawableName();
                        String drawableName2 = o2.getDrawableName();

                        if (!drawableName1.contains("@")) {
                            return -1;
                        }

                        if (!drawableName2.contains("@")) {
                            return 1;
                        }

                        if (drawableName1.contains("@2x") && drawableName2.contains("@3x")) {
                            return -1;
                        }

                        if (drawableName1.contains("@3x") && drawableName2.contains("@2x")) {
                            return 1;
                        }

                        return 0;
                    }
                });

            }

        }

        return result;
    }

    @Nullable
    private Drawables readOneDrawables(File resFolder) {

        if (resFolder == null || !resFolder.exists() || resFolder.isFile()) {
            return null;
        }

        // 拿到资源的名称
        String drawableName = resFolder.getName().substring(0, resFolder.getName().indexOf(".imageset"));

        Drawables drawables = new Drawables(drawableName);

        List<Drawable> drawableList = drawables.getDrawables();

        File[] drawableFiles = resFolder.listFiles();

        if (drawableFiles != null) {

            for (File drawableFile : drawableFiles) {

                if (isImageFile(drawableFile)) {

                    String imagePath = drawableFile.getPath();

                    imagePath = imagePath.replace(ProjectUtil.rootFilder.getPath(), "");

                    if (imagePath.length() > 0 && imagePath.startsWith(System.getProperty("file.separator"))) {
                        imagePath = imagePath.substring(System.getProperty("file.separator").length());
                    }

                    Drawable drawable = new Drawable(drawableFile.getName(), imagePath);
                    drawableList.add(drawable);

                    try {
                        BufferedImage bufferedImage = ImageIO.read(drawableFile);
                        drawable.setWidth(bufferedImage.getWidth());
                        drawable.setHeight(bufferedImage.getHeight());
                    } catch (IOException e) {
                    }

                }

            }

        }

        return drawables;

    }

    /**
     * 读取资源
     *
     * @param resFolder 可能本身是一个类别 也可能是类别的父文件夹
     */
    private void readFolder(ArrayList<DrawableCategory> categories, File resFolder) {

        if (resFolder == null || !resFolder.exists() || resFolder.isFile()) {
            return;
        }

        DrawableCategory category = new DrawableCategory(resFolder.getName());

        Map<String, Drawables> drawablesMap = new HashMap<>();

        File[] itemFiles = resFolder.listFiles();

        if (itemFiles == null) {

            return;

        }

        for (File itemFile : itemFiles) {

            if (itemFile.isDirectory()) {

                if (itemFile.getName().endsWith(".imageset")) { // 如果是一个资源

                    Drawables drawables = readOneDrawables(itemFile);
                    drawables.setDrawableCategory(new DrawableCategory(category.getName()));

                    category.getDrawablesList().add(drawables);

                } else {

                    readFolder(categories, itemFile);

                }

            } else { // 如果是一个文件

                if (isImageFile(itemFile)) { // 如果是一个图片

                    int index = itemFile.getName().lastIndexOf(".");

                    // 拿到没有后缀的文件名称
                    String drawableName = itemFile.getName().substring(0, index);

                    // 检查是否带有@字符串,这个表示这个是 IOS 中的几倍图
                    index = itemFile.getName().lastIndexOf("@");

                    // 如果有的话进一步提取文件名称
                    if (index != -1) {
                        drawableName = drawableName.substring(0, index);
                    }

                    Drawables drawables = drawablesMap.get(drawableName);

                    if (drawables == null) {

                        drawables = new Drawables(drawableName);
                        drawablesMap.put(drawableName, drawables);

                    }

                    String imagePath = itemFile.getPath();

                    imagePath = imagePath.replace(ProjectUtil.rootFilder.getPath(), "");

                    if (imagePath.length() > 0 && imagePath.startsWith(System.getProperty("file.separator"))) {
                        imagePath = imagePath.substring(System.getProperty("file.separator").length());
                    }

                    Drawable drawable = new Drawable(itemFile.getName(), itemFile.getName(), imagePath);

                    try {
                        BufferedImage bufferedImage = ImageIO.read(itemFile);
                        drawable.setWidth(bufferedImage.getWidth());
                        drawable.setHeight(bufferedImage.getHeight());
                    } catch (IOException e) {
                    }

                    drawables.getDrawables().add(drawable);

                }

            }

        }

        Set<Map.Entry<String, Drawables>> entries = drawablesMap.entrySet();

        for (Map.Entry<String, Drawables> entry : entries) {

            Drawables drawables = entry.getValue();

            category.getDrawablesList().add(drawables);

        }

        // 如果该类别下面的drawable个数不是0说明不是空的,那么就让前端展示这个类别
        if (category.getDrawablesList().size() > 0) {
            categories.add(category);
        }

    }

    private ProjectDrawable doReadList(Project pro) {

        ProjectDrawable projectDrawable = new ProjectDrawable(Project.ProjectType.INSTANCE.getTYPE_IOS());

        ArrayList<DrawableCategory> categories = projectDrawable.getCategories();

        String resPaths = pro.getResPath();

        String[] resPathsArr = resPaths.split(";");

        if (resPathsArr != null && resPathsArr.length > 0) {

            for (String path : resPathsArr) {
                // 这个文件夹下面的每一个文件夹都是一个类别
                File resFolder = new File(ProjectUtil.getProjectPath(pro), path);
                readFolder(categories, resFolder);
            }

        }

        return projectDrawable;

    }

    private boolean isImageFile(File file) {

        if(file == null) return false;

        if(!file.exists()) return false;

        if(file.isDirectory()) return false;

        if (file.getName().toLowerCase().endsWith(".png")
                || file.getName().toLowerCase().endsWith(".jpg")
                || file.getName().toLowerCase().endsWith(".gif")) {
            return true;
        }

        return false;

    }

}
