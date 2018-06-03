package com.xiaojinzi.image.bean

/**
 * Android项目
 */
class Project {

    val id: Int? = null

    var name: String? = null

    var remoteAddress: String? = null

    var branch: String? = null

    constructor()

    constructor(name: String?, remoteAddress: String?) {
        this.name = name
        this.remoteAddress = remoteAddress
    }


}