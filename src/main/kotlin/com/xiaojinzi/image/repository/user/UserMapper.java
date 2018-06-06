package com.xiaojinzi.image.repository.user;

import com.xiaojinzi.image.base.dao.BaseDao;
import com.xiaojinzi.image.bean.User;
import org.apache.ibatis.annotations.Param;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UserMapper extends BaseDao<User, Integer> {

    @Nullable
    User queryByNameAndPassword(@NotNull @Param("name") String name, @NotNull @Param("pass") String password);

}
