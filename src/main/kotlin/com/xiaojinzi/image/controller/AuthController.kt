package com.xiaojinzi.image.controller

import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestMapping
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("auth")
class AuthController {

    @Autowired
    var userService: UserService? = null;

    @RequestMapping("login")
    fun login(request: HttpServletRequest, response: HttpServletResponse) {

        userService!!.insert(User("xiaojinzi", "qwe"))

        val printWriter = PrintWriter(response.outputStream);

        printWriter.print("hello world");

        printWriter.flush();

    }

}