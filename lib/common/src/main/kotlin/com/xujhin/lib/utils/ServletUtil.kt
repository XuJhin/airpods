package com.xujhin.lib.utils

import com.alibaba.fastjson2.JSON
import com.xujhin.lib.core.domain.R
import org.apache.commons.lang3.StringUtils
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import reactor.core.publisher.Mono
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


open class ServletUtil {
    companion object {
        /**
         * 设置webflux模型响应
         *
         * @param response ServerHttpResponse
         * @param value 响应内容
         * @return Mono<Void>
        </Void> */
        fun webFluxResponseWriter(response: ServerHttpResponse, value: Any): Mono<Void> {
            return webFluxResponseWriter(response, HttpStatus.OK, value, R.FAIL)
        }

        /**
         * 设置webflux模型响应
         *
         * @param response ServerHttpResponse
         * @param code 响应状态码
         * @param value 响应内容
         * @return Mono<Void>
        </Void> */
        fun webFluxResponseWriter(response: ServerHttpResponse, value: String, code: Int): Mono<Void> {
            return webFluxResponseWriter(response, HttpStatus.OK, value, code)
        }

        /**
         * 设置webflux模型响应
         *
         * @param response ServerHttpResponse
         * @param status http状态码
         * @param code 响应状态码
         * @param value 响应内容
         * @return Mono<Void>
        </Void> */
        fun webFluxResponseWriter(
            response: ServerHttpResponse,
            status: HttpStatus?,
            value: Any,
            code: Int
        ): Mono<Void> {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, status, value, code)
        }

        /**
         * 设置webflux模型响应
         *
         * @param response ServerHttpResponse
         * @param contentType content-type
         * @param status http状态码
         * @param code 响应状态码
         * @param value 响应内容
         * @return Mono<Void>
        </Void> */
        fun webFluxResponseWriter(
            response: ServerHttpResponse,
            contentType: String?,
            status: HttpStatus?,
            value: Any,
            code: Int
        ): Mono<Void> {
            response.setStatusCode(status)
            response.headers.add(HttpHeaders.CONTENT_TYPE, contentType)
            val result: R<Any> = R.fail(code, value.toString())
            val dataBuffer: DataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).toByteArray())
            return response.writeWith(Mono.just(dataBuffer))
        }

        fun urlEncode(str: String): String {
            return try {
                URLEncoder.encode(str, "utf-8")
            } catch (e: UnsupportedEncodingException) {
                StringUtils.EMPTY
            }
        }
    }
}