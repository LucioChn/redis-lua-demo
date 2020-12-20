package com.nobody.lua.service;

import com.nobody.lua.util.RedisUtils;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Mr.nobody
 * @Date 2020/12/20
 * @Version 1.0
 */
@Service
public class OrderService {

    private RedisUtils redisUtils;

    public OrderService(final RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    // public String order(String productId, String userId) {
    //
    // String lockKey = "order:" + productId;
    //
    // Boolean lockSuccess = redisUtils.setNxWithExpireTime(lockKey, userId, 3000);
    // if (lockSuccess) {
    // try {
    // // do something
    // } finally {
    // redisUtils.delete(lockKey);
    // }
    // }
    //
    // return null;
    // }

    public boolean order(String productId, String userId) {
        boolean result = false;
        String lockKey = "order:" + productId;
        boolean lockSuccess = redisUtils.getLock(lockKey, userId, 3000);
        if (lockSuccess) {
            try {
                // do something
                result = true;
            } finally {
                boolean b = redisUtils.releaseLock(lockKey, userId);
                System.out.println(b);
            }
        }
        return result;
    }

}
