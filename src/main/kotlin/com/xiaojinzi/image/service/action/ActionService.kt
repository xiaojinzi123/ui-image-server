package com.xiaojinzi.image.service.action

import com.xiaojinzi.image.base.service.BaseService
import com.xiaojinzi.image.bean.Action
import com.xiaojinzi.image.bean.Project
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.util.ActionExistException

interface ActionService : BaseService<Action, Int> {

    @Throws(ActionExistException::class)
    override fun insert(t: Action?): Boolean

    fun isDrawableHaveOperation(sourceCategory: String?, source: String?): Boolean

}
