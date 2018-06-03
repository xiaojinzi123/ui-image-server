package com.xiaojinzi.image.init

import com.xiaojinzi.image.bean.Project
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList

@Repository
class InitApplication : ApplicationListener<ApplicationEvent>{

    var projects = ArrayList<Project>();

    override fun onApplicationEvent(event: ApplicationEvent?) {

        ProjectManager.getInstance().init();

    }

}