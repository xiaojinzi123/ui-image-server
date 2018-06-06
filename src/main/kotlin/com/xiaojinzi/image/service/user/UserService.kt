package com.xiaojinzi.image.service.user

import com.xiaojinzi.image.base.service.BaseService
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.util.LoginException
import java.io.IOException
import javax.security.auth.login.LoginContext

interface UserService : BaseService<User, Int> {

    /**
     * 登录返回一个User信息,如果登录失败会抛出一个异常
     * @see LoginException
     */
    @Throws(LoginException::class)
    fun login(name: String, password: String): User

}
