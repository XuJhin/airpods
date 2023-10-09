package com.xujhin.lib.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = ["local.config.enabled"], matchIfMissing = true)
open class LocalServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    open fun localConfigProperties(): LocalServiceConfigProperties {
        return LocalServiceConfigProperties()
    }




}