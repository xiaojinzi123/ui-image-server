package com.xiaojinzi.image.init

import com.xiaojinzi.image.bean.Project
import com.xiaojinzi.image.service.project.ProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Repository
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList

@Repository
class InitApplication : ApplicationListener<ApplicationEvent> {

    @Autowired
    var projectService: ProjectService? = null

    override fun onApplicationEvent(event: ApplicationEvent?) {

        projectService!!.queryAll().forEach(Consumer {
            ProjectManager.getInstance().addProject(it)
        })

        ProjectManager.getInstance().init();

    }

}