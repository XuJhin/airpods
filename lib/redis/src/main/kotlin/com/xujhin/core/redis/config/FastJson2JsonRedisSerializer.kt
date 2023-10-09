package com.xujhin.core.redis.config

import com.alibaba.fastjson2.JSONReader
import com.xujhin.lib.constant.Constants
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException
import java.nio.charset.Charset


/**
 * Redis使用FastJson序列化
 *
 * @author ruoyi
 */
class FastJson2JsonRedisSerializer<T>(private val clazz: Class<T>) : RedisSerializer<T?> {
    @Throws(SerializationException::class)
    override fun serialize(t: T?): ByteArray {
        return if (t == null) {
            ByteArray(0)
        } else com.alibaba.fastjson2.JSON.toJSONString(t, com.alibaba.fastjson2.JSONWriter.Feature.WriteClassName)
            .toByteArray(
                DEFAULT_CHARSET
            )
    }

    @Throws(SerializationException::class)
    override fun deserialize(bytes: ByteArray): T? {
        if (bytes == null || bytes.size <= 0) {
            return null
        }
        val str = String(bytes, DEFAULT_CHARSET)
        return com.alibaba.fastjson2.JSON.parseObject<T>(str, clazz, AUTO_TYPE_FILTER)
    }

    companion object {
        val DEFAULT_CHARSET = Charset.forName("UTF-8")
        val AUTO_TYPE_FILTER: com.alibaba.fastjson2.filter.Filter =
            JSONReader.autoTypeFilter(Constants.JSON_WHITELIST_STR)
    }
}

