package com.xujhin.community

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.xujhin.core","com.xujhin.community","com.xujhin.lib"])
open class CommunityApplication

fun main(args: Array<String>) {
    runApplication<CommunityApplication>(*args)
}
