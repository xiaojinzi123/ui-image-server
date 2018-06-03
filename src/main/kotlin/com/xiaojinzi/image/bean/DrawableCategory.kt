package com.xiaojinzi.image.bean

import java.util.ArrayList

/**
 * 表示一个资源的类别
 */
class DrawableCategory {

    // 类别的名称
    var name: String? = null

    // 该类别的图片资源
    var drawablesList = ArrayList<Drawables>()

    constructor(name: String?) {
        this.name = name
    }

}
