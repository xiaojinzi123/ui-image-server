package com.xiaojinzi.image.bean

import com.xiaojinzi.image.bean.Project.ProjectType.TYPE_ANDROID
import com.xiaojinzi.image.bean.Project.ProjectType.TYPE_IOS
import com.xiaojinzi.image.init.AndroidDrawableRead
import com.xiaojinzi.image.init.DrawableRead
import com.xiaojinzi.image.init.IosDrawableRead

/**
 * Android项目
 */
class Project {

    object ProjectType {

        val TYPE_ANDROID: String = "Android";
        val TYPE_IOS: String = "IOS";

    }

    val id: Int? = null

    var name: String? = null

    var remoteAddress: String? = null

    var branch: String? = null

    var gitName: String? = null

    var gitPass: String? = null

    var resPath: String? = null

    var projectType: String? = null

    // 额外的字段, 表示资源读取的实现
    var drawableRead: DrawableRead? = null

    fun tryGetDrawableRead(): DrawableRead? {

        if (drawableRead == null) {

            if (TYPE_ANDROID.equals(projectType)) {
                drawableRead = AndroidDrawableRead();
            } else if (TYPE_IOS.equals(projectType)) {
                drawableRead = IosDrawableRead();
            }

        }

        return drawableRead;

    }

    constructor()

    constructor(name: String?, remoteAddress: String?) {
        this.name = name
        this.remoteAddress = remoteAddress
    }


}