package com.xujhin.gateway.filter

import com.xujhin.auth.jwt.TokenConstants
import com.xujhin.core.redis.service.RedisService
import com.xujhin.gateway.config.propertoes.IgnoreWhiteProperties
import com.xujhin.lib.constant.CacheConstants
import com.xujhin.lib.constant.SecurityConstants
import com.xujhin.lib.utils.JwtManager
import com.xujhin.lib.utils.ServletUtil
import com.xujhin.lib.utils.StringUtil
import io.jsonwebtoken.Claims
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
@Order(value = -200)
class AuthFilter : GlobalFilter, Ordered {

    private val logger: Logger = LoggerFactory.getLogger(AuthFilter::class.java)

    @Autowired
    private lateinit var ignoreWhite: IgnoreWhiteProperties

    @Autowired
    lateinit var redisService: RedisService

    @Autowired
    lateinit var jwtManager:JwtManager

    companion object {
        const val GATEWAY_PREFIX = "/api"
    }

    private fun getToken(request: ServerHttpRequest): String? {
        var token = request.headers.getFirst(TokenConstants.AUTHENTICATION)
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX.toRegex(), StringUtils.EMPTY)
        }
        return token
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

//        val requiredAttribute: LinkedHashSet<URI> = exchange.getRequiredAttribute("")
//        val request = exchange.request
//        var requestUri = request.path.pathWithinApplication().value()
//        if (!requiredAttribute.isNullOrEmpty()) {
//            val iterator = requiredAttribute.iterator()
//            while (iterator.hasNext()) {
//                val next = iterator.next()
//                if (next.path.startsWith(GATEWAY_PREFIX)){
//                    requestUri=next.path.substring(GATEWAY_PREFIX.length)
//                }
//            }
//        }
//        val method=request.method.toString()
//        Basc
        logger.debug(exchange.request.uri.toString())
        val request = exchange.request
        val mutate = request.mutate()
        val path = request.uri.path.toString()

        if (StringUtil.matches(path, ignoreWhite.getWhites())) {
            return chain.filter(exchange)
        }

        val token = getToken(request)
        if (token.isNullOrEmpty()) {
            return unauthorizedResponse(exchange, "token 为空")
        }
        val claims: Claims = jwtManager.parseToken(token)
        if (claims.isNullOrEmpty()) {
            return unauthorizedResponse(exchange, "token无效")
        }
        val userKey = jwtManager.getUserKey(claims)
        val isLogin = redisService.hasKey(getTokenKey(userKey))
        if (!isLogin) {
            return unauthorizedResponse(exchange, "登录失效")
        }
        val userId = jwtManager.getUserID(claims)
        addHeader(mutate, SecurityConstants.USER_KEY, userKey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userId);
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);
        return chain.filter(exchange.mutate().request(mutate.build()).build());

    }

    private fun getTokenKey(token: String): String {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    private fun addHeader(mutate: ServerHttpRequest.Builder, name: String, value: Any?) {
        if (value == null) {
            return
        }
        val valueStr = value.toString()
        val valueEncode: String = ServletUtil.urlEncode(valueStr)
        mutate.header(name, valueEncode)
    }

    private fun removeHeader(mutate: ServerHttpRequest.Builder, name: String) {
        mutate.headers { httpHeaders: HttpHeaders -> httpHeaders.remove(name) }.build()
    }

    private fun unauthorizedResponse(exchange: ServerWebExchange, msg: String): Mono<Void> {
        logger.debug("[鉴权异常处理]请求路径:{}", exchange.request.path)
        return ServletUtil.webFluxResponseWriter(exchange.response, msg, HttpStatus.UNAUTHORIZED.value())
    }

    override fun getOrder(): Int {
        return -200
    }
}