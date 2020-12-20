package com.nobody.lua.util;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Mr.nobody
 * @Date 2020/12/20
 * @Version 1.0
 */
@Component
public class RedisUtils {

    private StringRedisTemplate stringRedisTemplate;

    public RedisUtils(final StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String LOCK_LUA_SCRIPT =
            "if redis.call('SETNX', KEYS[1], ARGV[1]) == 1 then redis.call('PEXPIRE', KEYS[1], ARGV[2]) return 1 else return 0 end";
    private static final String RELEASE_LOCK_LUA_SCRIPT =
            "if redis.call('GET', KEYS[1]) == ARGV[1] then return redis.call('DEL', KEYS[1]) else return 0 end";
    private static final Long RELEASE_LOCK_SUCCESS_RESULT = 1L;

    public boolean getLock(final String lockKey, final String value, final long timeout) {
        return Objects.equals(stringRedisTemplate
                .execute((RedisConnection connection) -> connection.eval(LOCK_LUA_SCRIPT.getBytes(),
                        ReturnType.INTEGER, 1, lockKey.getBytes(), value.getBytes(),
                        String.valueOf(timeout).getBytes())),
                RELEASE_LOCK_SUCCESS_RESULT);
    }

    public boolean releaseLock(final String lockKey, final String value) {
        return Objects.equals(stringRedisTemplate.execute(
                (RedisConnection connection) -> connection.eval(RELEASE_LOCK_LUA_SCRIPT.getBytes(),
                        ReturnType.INTEGER, 1, lockKey.getBytes(), value.getBytes())),
                RELEASE_LOCK_SUCCESS_RESULT);
    }

    public Boolean setNxWithExpireTime(String key, String value, long timeout) {
        Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
        if (null != ifAbsent && ifAbsent) {
            expireWithMilliseconds(key, timeout);
        }
        return ifAbsent;
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void expireWithMilliseconds(String key, long timeout) {
        stringRedisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

}
