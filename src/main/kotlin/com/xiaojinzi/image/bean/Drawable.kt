package com.xiaojinzi.image.bean

/**
 * 表示一个资源的单体,最小单位了
 */
class Drawable {

    // 资源的名称
    var drawableName: String? = null

    // 资源的路径,是相对于相对路径
    var imagePath: String? = null

    constructor()

    constructor(drawableName: String?, imagePath: String?) {
        this.drawableName = drawableName
        this.imagePath = imagePath
    }


}
