package com.xiaojinzi.image.controller

import com.xiaojinzi.image.Result
import com.xiaojinzi.image.Result.Companion.ERROR_TOKENEXPIRE
import com.xiaojinzi.image.bean.Action
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.service.action.ActionService
import com.xiaojinzi.image.service.user.UserService
import com.xiaojinzi.image.util.LoginException
import com.xiaojinzi.image.util.TokenGeneratorManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("action")
class ActionController {

    @Autowired
    var actionService: ActionService? = null

    /**
     * 添加一个资源到
     */
    @PostMapping()
    @ResponseBody
    fun addAction(request: HttpServletRequest, response: HttpServletResponse, source: String?): Result<Action>? {

        if (source == null || source.trim().length == 0) {

            return Result("parameter 'source' can't be null")

        }

        return null

    }

    @DeleteMapping()
    @ResponseBody
    fun deleteAction(request: HttpServletRequest, response: HttpServletResponse, sourceCategory: String?, source: String?): Result<Action>? {

        if (source == null || source.trim().length == 0) {

            return Result("parameter 'sourceCategory' can't be null")

        }

        if (source == null || source.trim().length == 0) {

            return Result("parameter 'source' can't be null")

        }

        val action = Action(Action.ACTION.ACTION_DELETE, source,sourceCategory)

        val b = actionService!!.insert(action)

        if (b) {
            return Result(action)
        } else {
            return Result("fail to delete")
        }

    }

    @PutMapping()
    @ResponseBody
    fun moveAction(request: HttpServletRequest, response: HttpServletResponse,
                   sourceCategory: String?, source: String?, targetCategory: String?): Result<Action>? {

        if (source == null || source.trim().length == 0) {

            return Result("parameter 'sourceCategory' can't be null")

        }

        if (source == null || source.trim().length == 0) {

            return Result("parameter 'source' can't be null")

        }

        if (targetCategory == null || targetCategory.trim().length == 0) {

            return Result("parameter 'targetCategory' can't be null")

        }

        val action = Action(Action.ACTION.ACTION_MOVE, source, sourceCategory, null, targetCategory)

        val b = actionService!!.insert(action)

        if (b) {
            return Result(action)
        } else {
            return Result("fail to move")
        }

    }

}