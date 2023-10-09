package com.xujhin.gateway.handler

import com.xujhin.lib.utils.ServletUtil
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.cloud.gateway.support.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class GatewayExceptionHandler : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response
        if (response.isCommitted) {
            return Mono.error(ex)
        }
        val msg = when (ex) {
            is NotFoundException -> {
                "服务未找到"
            }

            is ResponseStatusException -> {
                ex.message ?: "error"
            }

            else -> {
                "内部服务错误"
            }
        }
        return ServletUtil.webFluxResponseWriter(response, msg, HttpStatus.UNAUTHORIZED.value())
    }
}