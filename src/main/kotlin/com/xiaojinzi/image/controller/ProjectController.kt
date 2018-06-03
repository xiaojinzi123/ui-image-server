package com.xiaojinzi.image.controller

import com.google.gson.Gson
import com.xiaojinzi.image.Result
import com.xiaojinzi.image.bean.DrawableCategory
import com.xiaojinzi.image.bean.Project
import com.xiaojinzi.image.bean.ProjectDrawable
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.init.ProjectManager
import com.xiaojinzi.image.service.project.ProjectService
import com.xiaojinzi.image.service.user.UserService
import com.xiaojinzi.image.util.BusyException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("project")
class ProjectController {

    private var gson: Gson = Gson()

    @Autowired
    var projectService: ProjectService? = null;

    /**
     * 获取所有的Android项目
     */
    @RequestMapping("list")
    @ResponseBody
    fun list(request: HttpServletRequest, response: HttpServletResponse): Result<List<Project>> {

        val projects = projectService!!.queryAll();

        return Result(projects);

    }

    /**
     * 获取某一个安卓项目的图片资源
     */
    @RequestMapping("listImage")
    @ResponseBody
    fun listImage(request: HttpServletRequest, response: HttpServletResponse, proId: Int?): Result<ProjectDrawable>? {

        if (proId == null) {

            return Result("proId 不能为空")

        }

        // 拿到客户端需要获取的项目
        val project:Project? = projectService!!.queryById(proId)

        if (project == null) {
            return Result("没有找到目标项目")
        }

        try {

            val projectDrawable = ProjectManager.getInstance().readList(project)

            return Result(projectDrawable!!)

        } catch (e: BusyException) {
            return Result(e.message!!);
        }

    }

}