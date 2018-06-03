package com.xiaojinzi.image.service.project

import com.xiaojinzi.image.base.dao.BaseDao
import com.xiaojinzi.image.base.service.BaseServiceImpl
import com.xiaojinzi.image.bean.Project
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.repository.project.ProjectMapper
import com.xiaojinzi.image.repository.user.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("projectService")
open class ProjectServiceImpl : BaseServiceImpl<Project>(), ProjectService {

    @Autowired
    internal var projectMapper: ProjectMapper? = null

    override fun getBaseDao(): BaseDao<Project, Int>? {
        return projectMapper
    }


}
