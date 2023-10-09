package com.xujhin.lib

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.client.RestTemplate
import java.time.Duration

@SpringBootApplication(scanBasePackages = ["com.xujhin.lib"])
open class LibApplication

fun main(args: Array<String>) {
    runApplication<LibApplication>(*args)
}

