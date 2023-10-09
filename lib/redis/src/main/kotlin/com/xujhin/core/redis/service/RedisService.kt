package com.xujhin.core.redis.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit


@Component
open class RedisService {
    @Autowired
    lateinit var stringRedisTemplate: RedisTemplate<String, Any>

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    fun <T : Any> setCacheObject(key: String, value: T) {
        stringRedisTemplate.opsForValue().set(key, value)
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    fun <T : Any> setCacheObject(key: String, value: T, timeout: Long, timeUnit: TimeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout!!, timeUnit)
    }
    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    @JvmOverloads
    fun expire(key: String, timeout: Long, unit: TimeUnit = TimeUnit.SECONDS): Boolean {
        return stringRedisTemplate.expire(key, timeout, unit)
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    fun getExpire(key: String): Long {
        return stringRedisTemplate.getExpire(key)
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    fun hasKey(key: String): Boolean {
        return stringRedisTemplate.hasKey(key)
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    fun <T : Any> getCacheObject(key: String): Any {
        val operation = stringRedisTemplate.opsForValue()
        return operation[key]
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    fun deleteObject(key: String): Boolean {
        return stringRedisTemplate.delete(key)
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    fun deleteObject(collection: Collection<String>): Boolean {
        return stringRedisTemplate.delete(collection) > 0
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    fun <T : Any> setCacheList(key: String, dataList: List<T>?): Long {
        val count = stringRedisTemplate.opsForList().rightPushAll(key, dataList)
        return count ?: 0
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    fun <T : Any> getCacheList(key: String): MutableList<Any>? {
        return stringRedisTemplate.opsForList().range(key, 0, -1)
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    fun <T : Any> setCacheSet(key: String, dataSet: Set<T>): BoundSetOperations<String, Any> {
        val setOperation: BoundSetOperations<String, Any> = stringRedisTemplate.boundSetOps(key)
        val it = dataSet.iterator()
        while (it.hasNext()) {
            setOperation.add(it.next())
        }
        return setOperation
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    fun <T : Any> getCacheSet(key: String): MutableSet<Any>? {
        return stringRedisTemplate.opsForSet().members(key)
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    fun <T : Any> setCacheMap(key: String, dataMap: Map<String?, T>?) {
        if (dataMap != null) {
            stringRedisTemplate.opsForHash<String, T>().putAll(key, dataMap)
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    fun <T : Any> getCacheMap(key: String): Map<String, T> {
        return stringRedisTemplate.opsForHash<String, T>().entries(key)
    }


    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    fun keys(pattern: String?): Collection<String> {
        return stringRedisTemplate.keys(pattern)
    }
}

