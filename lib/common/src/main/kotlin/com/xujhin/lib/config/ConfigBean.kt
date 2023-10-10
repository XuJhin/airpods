package com.xujhin.lib.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
open class ConfigBean {
    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

}