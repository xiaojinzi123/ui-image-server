package com.xiaojinzi.image.bean;

import java.util.ArrayList;

/**
 * 表示返回了一个项目的drawable资源
 */
public class ProjectDrawable {

    /**
     * 不同分辨率的文件夹的顺序
     */
    private ArrayList<String> drawableSorts = new ArrayList<>();

    /**
     * 全部的资源的分类
     */
    private ArrayList<DrawableCategory> categories = new ArrayList<>();

    public ArrayList<String> getDrawableSorts() {
        return drawableSorts;
    }

    public void setDrawableSorts(ArrayList<String> drawableSorts) {
        this.drawableSorts = drawableSorts;
    }

    public ArrayList<DrawableCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<DrawableCategory> categories) {
        this.categories = categories;
    }

}
