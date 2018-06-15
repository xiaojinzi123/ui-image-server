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

        return result;
    }

    @Nullable
    private Drawables readOneDrawables (File resFolder) {

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

                if (drawableFile.exists() && drawableFile.isFile() &&
                        (drawableFile.getName().toLowerCase().endsWith(".png") || drawableFile.getName().toLowerCase().endsWith(".jpg"))) {

                    String imagePath = drawableFile.getPath();

                    imagePath = imagePath.replace(ProjectUtil.proFilder.getPath(), "");

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

                if(itemFile.getName().endsWith(".imageset")) { // 如果是一个资源

                    Drawables drawables = readOneDrawables(itemFile);
                    drawables.setDrawableCategory(new DrawableCategory(category.getName()));

                    category.getDrawablesList().add(drawables);

                }else {

                    readFolder(categories, itemFile);

                }

            } else { // 如果是一个文件

                if (itemFile.getName().toLowerCase().endsWith(".png") ||
                        itemFile.getName().toLowerCase().endsWith(".jpg")) { // 如果是一个图片

                    int index = itemFile.getName().lastIndexOf(".");

                    String drawableName = itemFile.getName().substring(0, index);

                    index = itemFile.getName().lastIndexOf("@");

                    if (index != -1) {
                        drawableName = drawableName.substring(0, index);
                    }

                    Drawables drawables = drawablesMap.get(drawableName);

                    if (drawables == null) {

                        drawables = new Drawables(drawableName);
                        drawablesMap.put(drawableName, drawables);

                    }

                    Drawable drawable = new Drawable(itemFile.getName(), itemFile.getName(), imagePath);
                    drawables.add(drawable);

                    try {
                        BufferedImage bufferedImage = ImageIO.read(itemFile);
                        drawable.setWidth(bufferedImage.getWidth());
                        drawable.setHeight(bufferedImage.getHeight());
                    } catch (IOException e) {
                    }

                    drawables.getDrawables().add(new Drawable())

                }

            }

        }

        if (category.getDrawablesList().size() > 0) {
            categories.add(category);
        }

    }

    private ProjectDrawable doReadList(Project pro) {

        ProjectDrawable projectDrawable = new ProjectDrawable();

        ArrayList<DrawableCategory> categories = projectDrawable.getCategories();

        String resPaths = pro.getResPath();

        String[] resPathsArr = resPaths.split(";");

        if (resPathsArr != null && resPathsArr.length > 0) {

            for (String path : resPathsArr) {
                // 这个文件夹下面的每一个文件夹都是一个类别
                File resFolder = new File(ProjectUtil.getProjectPath(pro), path);
                readFolder(categories,resFolder);
            }

        }

        return projectDrawable;

    }


}
