package com.xujhin.lib.service

import com.xujhin.lib.cache.entity.CacheUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class RestService {
    @Autowired
    private lateinit var restBalanceTemplate: RestTemplate

    // 从用户服务获取数据
    fun getFromUser(url: String): MutableMap<String ,Any>? {
        return restBalanceTemplate.getForObject("http://user-server$url", MutableMap::class.java) as MutableMap<String, Any>?
    }

    // 获取用户服务缓存
    fun getUsers(ids: List<String>): Map<String, CacheUser> {
        if (CollectionUtils.isEmpty(ids)) {
            return HashMap<String, CacheUser>()
        }
        val cacheUserMaps: MutableMap<String, CacheUser> = HashMap<String, CacheUser>()
        val resultMap: Map<String, Any> = restBalanceTemplate.getForObject<Map<String, Any>>(
            "http://user-server/admin/user/u/internal?ids=" + java.lang.String.join(",", HashSet(ids)),
            MutableMap::class.java
        )
        val userMaps = resultMap["result"] as Map<String, Map<String, Any>>
        for ((key, value) in userMaps) {
            cacheUserMaps[key] = CacheUser(value)
        }
        return cacheUserMaps
    }
}