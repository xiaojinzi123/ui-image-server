package com.xiaojinzi.image.controller

import com.xiaojinzi.image.Result
import com.xiaojinzi.image.bean.Action
import com.xiaojinzi.image.init.ProjectUtil
import com.xiaojinzi.image.service.action.ActionService
import com.xiaojinzi.image.util.ActionExistException
import com.xiaojinzi.image.util.ImageFileManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.commons.CommonsMultipartFile
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

    /**
     * 更新一个资源
     */
    @PostMapping("upload")
    @ResponseBody
    fun updateAction(request: HttpServletRequest, @RequestParam("file") file: CommonsMultipartFile): Result<String>? {

        request.requestURL;

        val targetFile = ImageFileManager.getInstance().saveImage(file)

        if (targetFile == null) {
            return Result("fail to upload");
        } else {
            return Result(targetFile.path.replace(ProjectUtil.rootFilder.path, ""), "upload : " + file.originalFilename, true);
        }

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

        val action = Action(Action.ACTION.ACTION_DELETE, source, sourceCategory)

        try {

            val b = actionService!!.insert(action)

            if (b) {
                return Result(action)
            } else {
                return Result("fail to delete")
            }

        } catch (e: ActionExistException) {
            return Result(e.message)
        }

    }

    /**
     * 移动一个资源到其他的类别下面
     */
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

        try {
            val b = actionService!!.insert(action)

            if (b) {
                return Result(action)
            } else {
                return Result("fail to move")
            }
        } catch (e: ActionExistException) {
            return Result(e.message)
        }

    }

}