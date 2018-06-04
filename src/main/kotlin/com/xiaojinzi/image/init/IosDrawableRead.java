package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * 实现 IOS 项目的资源的读取
 */
public class IosDrawableRead implements DrawableRead {

    @Override
    @NotNull
    public ProjectDrawable read(Project pro) {

        // 这个文件夹下面的每一个文件夹都是一个类别
        File resFolder = new File(ProjectUtil.getProjectPath(pro), pro.getResPath());

        ProjectDrawable result = doReadList(resFolder);

        return result;
    }

    private ProjectDrawable doReadList(File resFolder) {

        ProjectDrawable projectDrawable = new ProjectDrawable();

        ArrayList<DrawableCategory> categories = projectDrawable.getCategories();

        if (resFolder.exists() && resFolder.isDirectory()) {

            File[] itemFiles = resFolder.listFiles();

            if (itemFiles != null) {

                for (File itemFile : itemFiles) {

                    if (itemFile.isDirectory()) {

                        DrawableCategory category = new DrawableCategory(itemFile.getName());

                        soveOneCategoryFolder(itemFile, category);

                        categories.add(category);

                    }

                }

            }

        }

        return projectDrawable;

    }

    private void soveOneCategoryFolder(File itemFile, DrawableCategory category) {

        ArrayList<Drawables> drawablesList = category.getDrawablesList();

        File[] files = itemFile.listFiles();

        if (files != null) {

            for (File drawableFolder : files) {

                if (!drawableFolder.exists() || drawableFolder.isFile()) {
                    continue;
                }

                String drawableName = drawableFolder.getName();

                if (drawableName.endsWith(".imageset")) {

                    drawableName = drawableName.substring(0, drawableName.indexOf(".imageset"));

                }

                Drawables drawables = new Drawables(drawableName);

                List<Drawable> drawableList = drawables.getDrawables();

                File[] drawableFiles = drawableFolder.listFiles();

                if (drawableFiles != null) {

                    for (File drawableFile : drawableFiles) {

                        if (drawableFile.exists() && drawableFile.isFile() &&
                                (drawableFile.getName().toLowerCase().endsWith(".png") || drawableFile.getName().toLowerCase().endsWith(".jpg"))) {

                            String imagePath = drawableFile.getPath();

                            imagePath = imagePath.replace(ProjectUtil.proFilder.getPath(), "");

                            if (imagePath.length() > 0 && imagePath.startsWith(System.getProperty("file.separator"))) {
                                imagePath = imagePath.substring(System.getProperty("file.separator").length());
                            }

                            drawableList.add(new Drawable(drawableFile.getName(), imagePath));

                        }

                    }

                }

                drawablesList.add(drawables);


            }

        }

    }

}
