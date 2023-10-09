package com.xujhin.gateway.filter

import lombok.extern.log4j.Log4j2
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.InetSocketAddress


@Log4j2
@Component
class AccessLogGlobalFilter : GlobalFilter {
    private val logger: Logger = LoggerFactory.getLogger(AccessLogGlobalFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        val request: ServerHttpRequest = exchange.request
        val path: String = request.path.pathWithinApplication().value()
        val remoteAddress: InetSocketAddress? = request.remoteAddress
        return chain.filter(exchange).then(Mono.fromRunnable {
            val response: ServerHttpResponse = exchange.response
            val statusCode = response.statusCode
            logger.debug("请求路径:{},远程IP地址:{},响应码:{}", path, remoteAddress, statusCode)
            println("请求路径:${path},远程IP地址:${remoteAddress},响应码:${statusCode}")
        })
    }
}