package com.xujhin.community.entity

import com.xujhin.lib.cache.entity.CacheUser
import com.xujhin.lib.entity.BaseEntity

class CommentEntity : BaseEntity() {
    fun setMentionedUserMap(cacheUser: CacheUser?) {

    }

    fun setUserMap(cacheUser: CacheUser?) {

    }

    val userId: String = ""
    val deviceId: String = ""
    val mentionedId: String = ""
    val postId: String = ""
    val mentionedUserId:String=""
}