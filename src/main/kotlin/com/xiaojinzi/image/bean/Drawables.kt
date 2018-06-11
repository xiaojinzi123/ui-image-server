package com.xiaojinzi.image.bean

/**
 * 一个drawable资源,可能会有多张图片,在不同分辨率下面的
 * 所以内部会有一个集合
 */
class Drawables {

    // 所属的类别
    var drawableCategory: DrawableCategory? = null

    // 这个资源的名称,指的是同一个名字在不同分辨率下的资源
    // 这个就是资源的名字
    var drawableName: String? = null

    var drawables: List<Drawable>? = ArrayList()

    constructor(drawableCategory: DrawableCategory?, drawableName: String?) {
        this.drawableCategory = DrawableCategory(drawableCategory!!.name)
        this.drawableName = drawableName
    }

}
