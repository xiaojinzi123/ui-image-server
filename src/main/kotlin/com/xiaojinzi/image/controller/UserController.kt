package com.xiaojinzi.image.controller

import com.xiaojinzi.image.Result
import com.xiaojinzi.image.Result.Companion.ERROR_TOKENEXPIRE
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.service.user.UserService
import com.xiaojinzi.image.util.LoginException
import com.xiaojinzi.image.util.TokenGeneratorManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("user")
class UserController {

    @Autowired
    var userService: UserService? = null;

    @GetMapping("")
    @ResponseBody
    fun login(request: HttpServletRequest, response: HttpServletResponse, userToken: String?): Result<User> {

        if (userToken == null) {

            return Result("parameter 'userToken' is null")

        }

        val userId = TokenGeneratorManager.getUserId(userToken)

        if (userId == null) {

            // token 已经过期的错误
            return Result("token is expired", ERROR_TOKENEXPIRE)

        }

        val user = userService!!.queryById(userId)

        if (user == null) {

            // token 已经过期的错误
            return Result("token is expired", ERROR_TOKENEXPIRE)

        }

        return Result(user)

    }

}