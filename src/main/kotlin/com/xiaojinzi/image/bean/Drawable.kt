package com.xiaojinzi.image.bean

/**
 * 表示一个资源的单体,最小单位了
 */
class Drawable {

    // Android 资源属于哪一个drawable分辨率的文件夹
    // 客户端会判断这个值是否为空,显示出这个资源是哪个分辨率下的,IOS 的是没有这个值的,会使用 drawableName 属性,
    // 他们的这个 drawableName 属性的名称中有@2x,@3x这种可识别的标识
    var drawableFolderName: String? = null

    // 资源的名称
    var drawableName: String? = null

    // 资源的路径,是相对于相对路径
    var imagePath: String? = null

    var width: Int = 0;

    var height: Int = 0;

    constructor()

    constructor(drawableName: String?, imagePath: String?) {
        this.drawableName = drawableName
        this.imagePath = imagePath
    }

    constructor(drawableFolderName: String?, drawableName: String?, imagePath: String?) {
        this.drawableFolderName = drawableFolderName
        this.drawableName = drawableName
        this.imagePath = imagePath
    }


}
