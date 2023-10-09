package com.xujhin.lib.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "pods.jwt")
@Component
data class JwtProperties(
    var enabled: Boolean = false,
    var secret: String = "thisisajwt",
    var expiration: Long = 0,
    var header: String = "",
    var userParamName: String = "userId",
    var defaultController: Boolean = false,
    var skipValidUrl: MutableList<String> = arrayListOf(),
)