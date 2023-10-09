package com.xujhin.lib.config

import com.fasterxml.jackson.annotation.JsonIgnore
import com.xujhin.lib.config.LocalServiceConfigProperties.Companion.PREFIX
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.core.env.Environment

@ConfigurationProperties(PREFIX)
@RefreshScope
open class LocalServiceConfigProperties {
    companion object {
        const val PREFIX = "local.config"
    }

    var env: String = ""

    @Autowired
    @JsonIgnore
    private val environment: Environment? = null

    @PostConstruct
    fun init() {
        this.overrideFromEnv()
    }

    private fun overrideFromEnv() {


    }
}