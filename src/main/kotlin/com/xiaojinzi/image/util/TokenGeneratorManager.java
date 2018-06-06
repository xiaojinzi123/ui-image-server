package com.xiaojinzi.image.util;

import com.xiaojinzi.image.bean.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class TokenGeneratorManager {

    private static Map<String, Integer> map = new HashMap<String, Integer>();

    public static synchronized String genetate(@NotNull User user) {

        if (user.getId() == null) {
            throw new IllegalArgumentException("user'id is null");
        }

        String token = UUID.randomUUID() + String.valueOf(new Random().nextInt(10000));

        map.put(token, user.getId());

        return token;

    }

    /**
     * 根据token获取用户id
     *
     * @param token
     * @return
     */
    @Nullable
    public static synchronized Integer getUserId(String token) {

        if (token == null) {

            throw new IllegalArgumentException("token is null");

        }

        Integer userId = map.get(token);

        return userId;

    }

}
