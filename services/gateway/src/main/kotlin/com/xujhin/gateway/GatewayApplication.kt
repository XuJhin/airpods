package com.xujhin.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.client.RestTemplate


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = ["com.xujhin.core","com.xujhin.gateway","com.xujhin.lib"])
open class GatewayApplication

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)


}

