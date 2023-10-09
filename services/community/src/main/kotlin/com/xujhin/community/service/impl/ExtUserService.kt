package com.xujhin.community.service.impl

import com.xujhin.lib.service.RestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExtUserService {

    @Autowired
    private lateinit var restService: RestService

    // 检查用户状态是否合法
    fun checkBlack(userId: String, deviceId: String): String? {
        val map: MutableMap<String, Any>? = restService.getFromUser("/user/black/check?userId=$userId&deviceId=$deviceId")
        return map?.get("result")?.toString()
    }
}