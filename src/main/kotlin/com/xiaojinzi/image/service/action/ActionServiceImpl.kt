package com.xiaojinzi.image.service.action

import com.xiaojinzi.image.base.dao.BaseDao
import com.xiaojinzi.image.base.service.BaseServiceImpl
import com.xiaojinzi.image.bean.Action
import com.xiaojinzi.image.repository.action.ActionMapper
import com.xiaojinzi.image.util.ActionExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("actionService")
open class ActionServiceImpl : BaseServiceImpl<Action>(), ActionService {

    @Autowired
    internal var actionMapper: ActionMapper? = null

    override fun getBaseDao(): BaseDao<Action, Int>? {
        return actionMapper
    }

    @Transactional
    override fun isDrawableHaveOperation(sourceCategory: String?, source: String?): Boolean {

        val action = actionMapper!!.queryBySourceCategoryAndSource(sourceCategory!!, source!!)

        return action != null

    }

    @Throws(ActionExistException::class)
    @Transactional
    override fun insert(t: Action?): Boolean {

        var b = isDrawableHaveOperation(t!!.sourceCategory, t!!.source)

        if (b) {
            // throw ActionExistException("this drawable has a untreated operation")
            throw ActionExistException("这个资源有一个未处理的操作,请等处理完毕再操作此资源")
        } else {
            return super.insert(t)
        }

    }

}
