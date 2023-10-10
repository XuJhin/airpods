package com.xujhin.community.sentinel

import com.alibaba.csp.sentinel.slots.block.BlockException
import org.springframework.stereotype.Component

@Component

open class BlockedHandler {
    companion object {
        @JvmStatic
        fun helloBlocked(ex: BlockException): String {
            return "怎么说"
        }

    }
}