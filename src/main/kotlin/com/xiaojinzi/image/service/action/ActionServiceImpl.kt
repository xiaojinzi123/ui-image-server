package com.xiaojinzi.image.service.action

import com.xiaojinzi.image.base.dao.BaseDao
import com.xiaojinzi.image.base.service.BaseServiceImpl
import com.xiaojinzi.image.bean.Action
import com.xiaojinzi.image.bean.Project
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.repository.action.ActionMapper
import com.xiaojinzi.image.repository.project.ProjectMapper
import com.xiaojinzi.image.repository.user.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("actionService")
open class ActionServiceImpl : BaseServiceImpl<Action>(), ActionService {

    @Autowired
    internal var actionMapper: ActionMapper? = null

    override fun getBaseDao(): BaseDao<Action, Int>? {
        return actionMapper
    }

}
