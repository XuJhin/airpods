package com.xujhin.community.service.impl

import com.alibaba.csp.sentinel.annotation.SentinelResource
import com.xujhin.community.sentinel.BlockedHandler
import org.springframework.stereotype.Service

@Service
open class SentinelService {
    @SentinelResource(value = "hello", blockHandler = "helloBlocked", blockHandlerClass = [BlockedHandler::class])
    open fun hello(): String {
        return "hello sentinel"
    }

}