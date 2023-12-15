/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.common.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 通用Redis缓存操作器
 *
 * @author xuyuxiang
 * @date 2022/6/21 16:00
 **/
@Component
public class CommonCacheOperator {

    /** 所有缓存Key的前缀 */
    private static final String CACHE_KEY_PREFIX = "Cache:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void put(String key, Object value) {
        redisTemplate.boundValueOps(CACHE_KEY_PREFIX + key).set(value);
    }

    public void put(String key, Object value, long timeoutSeconds) {
        redisTemplate.boundValueOps(CACHE_KEY_PREFIX + key).set(value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public Object get(String key) {
        return redisTemplate.boundValueOps(CACHE_KEY_PREFIX + key).get();
    }

    public void remove(String... key) {
        ArrayList<String> keys = CollectionUtil.toList(key);
        List<String> withPrefixKeys = keys.stream().map(i -> CACHE_KEY_PREFIX + i).collect(Collectors.toList());
        redisTemplate.delete(withPrefixKeys);
    }

    public Collection<String> getAllKeys() {
        Set<String> keys = redisTemplate.keys(CACHE_KEY_PREFIX + "*");
        if (keys != null) {
            // 去掉缓存key的common prefix前缀
            return keys.stream().map(key -> StrUtil.removePrefix(key, CACHE_KEY_PREFIX)).collect(Collectors.toSet());
        } else {
            return CollectionUtil.newHashSet();
        }
    }

    public Collection<Object> getAllValues() {
        Set<String> keys = redisTemplate.keys(CACHE_KEY_PREFIX + "*");
        if (keys != null) {
            return redisTemplate.opsForValue().multiGet(keys);
        } else {
            return CollectionUtil.newArrayList();
        }
    }

    public Map<String, Object> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        HashMap<String, Object> results = MapUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }

    /**
     * 根据分组和key 获取value
     * @param group
     * @param key
     * @return
     */
    public Object get(String group, String key) {
        return redisTemplate.boundValueOps(group +":"+ key).get();
    }

    public Object getKeyAndDelete(String key){
        return  redisTemplate.execute(new SessionCallback<Object>(){
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                // 开启事务
                redisOperations.multi();

                // 查询键
                Object o = redisOperations.opsForValue().get(key);

                // 删除键
                redisOperations.delete(key);

                // 执行事务
                redisOperations.exec();
                return o;
            }
        });
    }

    /**
     * 根据前缀删除
     * @param key
     */
    public void delete(String key){
        Set<String> keys = redisTemplate.keys(key + "*");
        assert keys != null;
        redisTemplate.delete(keys);
    }

    /**
     * 设置hash 类型的缓存
     * @param key
     * @param hashKey
     * @param value
     */
    public void putHash(String key, String hashKey, String value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * hash 判断是否存在该值
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hasHashKey(String key, String hashKey){
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Boolean hasKey(String key){
        return  redisTemplate.hasKey(key);
    }


    /**
     * 缓存list
     * @param key
     * @param value
     */
    public void putList(String key, Object value) {
        BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps(key);
        if(boundListOps.size() ==0){
            boundListOps.leftPush(value);
        }else {
            boundListOps.leftPushIfPresent(value);
        }
    }

    /**
     * 获取key 的list缓存值
     * @param key
     * @return
     */
    public List<Object> getList(String key) {
        BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps(key);
        return boundListOps.range(0, -1);
    }

    /**
     * 带有过期时间
     * @param key
     * @param value
     * @param expire
     */
    public void putListExpire(String key, Object value, long expire) {
        BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps(key);
        boundListOps.expire(expire,TimeUnit.SECONDS);
        if(boundListOps.size() ==0){
            boundListOps.leftPush(value);
        }else {
            boundListOps.leftPushIfPresent(value);
        }
    }

    /**
     * 带有过期时间
     * @param key
     */
    public void removeListValue(String key) {
        BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps(key);
        boundListOps.rightPop();
    }

    /**
     *  set类型添加
     * @param key
     * @param value
     */
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(CACHE_KEY_PREFIX+key, value);
    }

    /**
     * 获取所有value
     * @param
     * @return
     */
    public Set<Object> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(CACHE_KEY_PREFIX+key);
    }

    /**
     * 移除value
     * @param key
     * @param value
     */
    public void removeFromSet(String key, String value) {
        redisTemplate.opsForSet().remove(CACHE_KEY_PREFIX+key, value);
    }

    /**
     * 判断某个value是否存在
     * @param key
     * @param value
     * @return
     */
    public boolean isValueInSet(String key, String value) {
        Set<Object> setMembers = getSetMembers(key);
        return setMembers != null && setMembers.contains(value);
    }
}
