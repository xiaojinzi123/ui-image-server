package com.xiaojinzi.image.bean;

import java.util.ArrayList;

/**
 * 表示返回了一个项目的drawable资源
 */
public class ProjectDrawable {

    /**
     * 全部的资源的分类
     */
    private ArrayList<DrawableCategory> categories = new ArrayList<>();

    public ArrayList<DrawableCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<DrawableCategory> categories) {
        this.categories = categories;
    }

}
