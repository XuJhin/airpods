package com.xujhin.lib.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
open class ConfigBean {
    // 负载REST
    @Bean
    open fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        builder.setConnectTimeout(Duration.ofMillis(5000))
        builder.setReadTimeout(Duration.ofMillis(5000))
        val restTemplate = builder.build()
//        restTemplate.interceptors.add(RestHeadInterceptor())
//        restTemplate.interceptors.add(RestLogInterceptor())
//        restTemplate.setErrorHandler(RestResponseErrorHandler())
        return restTemplate
    }

}