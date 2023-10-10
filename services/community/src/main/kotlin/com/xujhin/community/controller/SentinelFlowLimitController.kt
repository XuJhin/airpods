package com.xujhin.community.controller

import com.alibaba.csp.sentinel.Entry
import com.alibaba.csp.sentinel.SphU
import com.alibaba.csp.sentinel.annotation.SentinelResource
import com.alibaba.csp.sentinel.slots.block.BlockException
import com.xujhin.community.service.impl.CommentService
import com.xujhin.community.service.impl.SentinelService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/community")
@Slf4j
open class SentinelFlowLimitController {


    @Autowired
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var sentinelService: SentinelService


    @GetMapping("/greet")
    public fun greet(): String {
        return sentinelService.hello()
    }


    @GetMapping("/sentinel/a")
    fun sentinelA(): String {
        return testAbySphU()
    }

    /**
     * 通过 SphU 手动定义资源
     * @return
     */
    fun testAbySphU(): String {
        var entry: Entry? = null
        return try {
            entry = SphU.entry("testAbySphU")
            //您的业务逻辑 - 开始
            log.info("服务访问成功------testA")
            "服务访问成功------testA："
            //您的业务逻辑 - 结束
        } catch (e1: BlockException) {
            //流控逻辑处理 - 开始
            log.info("testA 服务被限流")
            "testA 服务被限流"
            //流控逻辑处理 - 结束
        } finally {
            entry?.exit()
        }
    }


    companion object {
        val log: Logger = LoggerFactory.getLogger(SentinelFlowLimitController::class.java)
    }


}