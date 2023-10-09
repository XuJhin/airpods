package com.xujhin.gateway.config.propertoes

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration


@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.ignore")
open class IgnoreWhiteProperties {
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private var whites: List<String>? = ArrayList()

    fun getWhites(): List<String> {
        return whites?: arrayListOf()
    }

    fun setWhites(whites: List<String>) {
        this.whites = whites
    }
}