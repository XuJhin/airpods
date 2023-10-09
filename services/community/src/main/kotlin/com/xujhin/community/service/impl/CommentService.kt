package com.xujhin.community.service.impl

import com.xujhin.community.db.CommunityDao
import com.xujhin.community.entity.CommentEntity
import com.xujhin.community.entity.PostEntity
import com.xujhin.community.mq.ActionProducer
import com.xujhin.community.service.ICommentService
import com.xujhin.community.utils.VerifyActionUtil
import com.xujhin.lib.cache.CacheKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class CommentService : ICommentService {

    @Autowired
    private lateinit var commentDao: CommunityDao

    @Autowired
    private lateinit var verifyActionUtil: VerifyActionUtil

    @Autowired
    private lateinit var actionProducer: ActionProducer

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var decorateMapService: DecorateMapService

    /**
     * 评论太快时，使用Sentinel 进行熔断
     */
    override fun commentPost(comment: CommentEntity): CommentEntity {

        if (comment.mentionedId.isNotEmpty()) {

        }


        val postEntity = commentDao.findOne(PostEntity::class.java, comment.postId)
        //动态已删除
        if (postEntity == null) {

        }
        verifyActionUtil.commentVerify(comment.userId, comment.mentionedId.ifEmpty { postEntity?.userId })
        val commentRes = commentDao.save(comment)
        run {
            //用户评论：通知消息队列
            actionProducer.sendUserComment(commentRes.userId, postEntity!!.id, commentRes.id)
            stringRedisTemplate.opsForList().leftPush(CacheKey.USER_ACHIEVEMENT_COMMENT_REFRESH, postEntity.userId);
        }

        // 设置自动推荐
        // 评论人去重 校验 防止刷评论
        run {

        }

        return decorateMapService.appendUserMapForComment(commentRes)

    }

    override fun commentComment(postId: String, commentId: String): CommentEntity {
        return CommentEntity()
    }

    override fun commentPage() {
    }

    override fun commentRaise() {
    }

    override fun commentDelete() {
    }

    fun hello(): String {
        return "hello"
    }
}