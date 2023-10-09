package com.xujhin.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.xujhin.lib", "com.xujhin.user", "com.xujhin.auth"])
open class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
