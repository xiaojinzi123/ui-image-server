package com.xiaojinzi.image.controller

import com.xiaojinzi.image.Result
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.service.user.UserService
import com.xiaojinzi.image.util.LoginException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("auth")
class AuthController {

    @Autowired
    var userService: UserService? = null;

    @RequestMapping("login")
    @ResponseBody
    fun login(request: HttpServletRequest, response: HttpServletResponse, name: String, password: String): Result<User> {

        try {

            var user: User = userService!!.login(name, password)

            return Result(user)

        } catch (e: LoginException) {
            return Result(e!!.message)
        }catch (e: Exception) {
            return Result("未知错误")
        }

    }

}