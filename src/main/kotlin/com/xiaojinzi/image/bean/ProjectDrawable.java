package com.xiaojinzi.image.bean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 表示返回了一个项目的drawable资源
 */
public class ProjectDrawable {

    @NotNull
    private String drawableType;

    /**
     * 全部的资源的分类
     */
    private ArrayList<DrawableCategory> categories = new ArrayList<>();

    public ArrayList<DrawableCategory> getCategories() {
        return categories;
    }

    public ProjectDrawable(@NotNull String drawableType) {
        this.drawableType = drawableType;
    }

    @NotNull
    public String getDrawableType() {
        return drawableType;
    }

}
