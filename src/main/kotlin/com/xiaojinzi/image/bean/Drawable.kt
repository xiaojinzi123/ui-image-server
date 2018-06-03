package com.xiaojinzi.image.bean

/**
 * 表示一个资源的单体,最小单位了
 */
class Drawable {

    // 所属的drawable文件夹目录名称
    var drawableName: String? = null

    //
    var imagePath: String? = null

    constructor()

    constructor(drawableName: String?, imagePath: String?) {
        this.drawableName = drawableName
        this.imagePath = imagePath
    }


}
