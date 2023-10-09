package com.xujhin.core.redis.config

import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@AutoConfigureBefore(RedisAutoConfiguration::class)
open class RedisConfig {

    @Bean
    open fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = connectionFactory
        val serializer = FastJson2JsonRedisSerializer(Any::class.java)
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = serializer
        // Hash的key也采用StringRedisSerializer的序列化方式
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = serializer
        template.afterPropertiesSet()
        return template
    }
}