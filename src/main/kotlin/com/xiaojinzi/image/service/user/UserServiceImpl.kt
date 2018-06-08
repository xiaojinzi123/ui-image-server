package com.xiaojinzi.image.service.user

import com.xiaojinzi.image.base.dao.BaseDao
import com.xiaojinzi.image.base.service.BaseServiceImpl
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.repository.user.UserMapper
import com.xiaojinzi.image.util.LoginException
import com.xiaojinzi.image.util.TokenGeneratorManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userService")
open class UserServiceImpl : BaseServiceImpl<User>(), UserService {

    @Autowired
    internal var userMapper: UserMapper? = null

    override fun getBaseDao(): BaseDao<User, Int>? {
        return userMapper
    }

    @Transactional
    @Throws(LoginException::class)
    override fun login(name: String, password: String): User {

        if (name == null || name.trim().length == 0) {
            throw LoginException("account can't be null")
        }

        if (password == null || password.trim().length == 0) {
            throw LoginException("password can't be null")
        }

        var user:User? = userMapper!!.queryByNameAndPassword(name, password)

        if (user == null) {

            throw LoginException("account or password is incorrect")

        }

        user.userToken = TokenGeneratorManager.genetate(user);

        return user

    }


}
