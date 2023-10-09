package com.xujhin.community.service.impl

import com.xujhin.community.entity.CommentEntity
import com.xujhin.core.redis.service.RedisService
import com.xujhin.lib.cache.entity.CacheUser
import com.xujhin.lib.service.RestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DecorateMapService {

    @Autowired
    private lateinit var redisService: RedisService

    @Autowired
    private lateinit var restService: RestService

    fun appendUserMapForComment(comment: CommentEntity): CommentEntity {
        val userIds: MutableList<String> = ArrayList()
        userIds.add(comment.userId)
        userIds.add(comment.mentionedId)
        val userMap: Map<String, CacheUser> = restService.getUsers(userIds)
        return comment.apply {
            setUserMap(userMap[comment.userId])
            setMentionedUserMap(if (comment.mentionedUserId.isEmpty()) userMap[comment.mentionedUserId] else null)
        }
    }
}