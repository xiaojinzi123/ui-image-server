package com.xiaojinzi.image.service.user

import com.xiaojinzi.image.base.dao.BaseDao
import com.xiaojinzi.image.base.service.BaseServiceImpl
import com.xiaojinzi.image.bean.User
import com.xiaojinzi.image.repository.user.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("userService")
open class UserServiceImpl : BaseServiceImpl<User>(), UserService {

    @Autowired
    internal var userMapper: UserMapper? = null

    override fun getBaseDao(): BaseDao<User, Int>? {
        return userMapper
    }


}
