package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 实现 Android 项目的资源的读取
 */
public class AndroidDrawableRead implements DrawableRead {

    @Override
    @NotNull
    public ProjectDrawable read(Project pro) {

        File resFolder = new File(ProjectUtil.getProjectPath(pro), pro.getResPath());

        ProjectDrawable result = doReadList(resFolder);

        return result;
    }

    private ProjectDrawable doReadList(File resFolder) {

        ProjectDrawable projectDrawable = new ProjectDrawable();

        ArrayList<DrawableCategory> result = new ArrayList<>();

        // key 为图片的名字,后面为同名的图片资源
        Map<String, List<Drawable>> map = new HashMap<>();

        File[] files = resFolder.listFiles();

        if (files != null) {
            for (File file : files) {

                // 如果是 drawable 文件夹
                if (file.exists() && file.isDirectory() && file.getName().startsWith("drawable")) {

                    soveDrawablFolder(file, map, projectDrawable);

                }

            }
        }

        // 将整理的资源文件转化成前端显示的形式

        DrawableCategory categoryAll = new DrawableCategory("全部");

        Set<Map.Entry<String, List<Drawable>>> entries = map.entrySet();

        for (Map.Entry<String, List<Drawable>> entry : entries) {

            String drawableName = entry.getKey();
            List<Drawable> drawables = entry.getValue();

            Drawables ds = new Drawables(categoryAll, drawableName);
            if (drawables != null) {
                ds.getDrawables().addAll(drawables);
            }

            categoryAll.getDrawablesList().add(ds);

        }

        result.add(categoryAll);

        projectDrawable.getCategories().addAll(result);

        return projectDrawable;

    }

    private void soveDrawablFolder(File drawableFolder, Map<String, List<Drawable>> map, ProjectDrawable projectDrawable) {

        if (drawableFolder == null || !drawableFolder.exists() || drawableFolder.isFile()) {
            return;
        }

        File[] files = drawableFolder.listFiles();

        if (files == null) {
            return;
        }

        for (File itemFile : files) {

            if (itemFile.isDirectory()) {
                continue;
            }

            String fileName = itemFile.getName();

            if (fileName.toLowerCase().endsWith(".png") ||
                    fileName.toLowerCase().endsWith(".jpg")) {

                List<Drawable> drawables = map.get(fileName);

                if (drawables == null) {

                    drawables = new ArrayList<>();
                    map.put(fileName, drawables);

                }

                // 物理机的真实路径,做一个转化
                String imagePath = itemFile.getPath();

                imagePath = imagePath.replace(ProjectUtil.proFilder.getPath(), "");

                if (imagePath.length() > 0 && imagePath.startsWith(System.getProperty("file.separator"))) {
                    imagePath = imagePath.substring(System.getProperty("file.separator").length());
                }

                Drawable drawable = new Drawable(drawableFolder.getName(), itemFile.getName(), imagePath);
                drawables.add(drawable);

                try {
                    BufferedImage bufferedImage = ImageIO.read(itemFile);
                    drawable.setWidth(bufferedImage.getWidth());
                    drawable.setHeight(bufferedImage.getHeight());
                } catch (IOException e) {
                }

            }

        }

    }

}
