package com.xujhin.community.service

import com.xujhin.community.entity.CommentEntity
import com.xujhin.lib.entity.BaseResp
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody

interface ICommentService {

    /**
     * 评论动态
     */
    fun commentPost(@RequestBody @Validated  obj: CommentEntity):CommentEntity

    /**
     * 评论评论
     */
    fun commentComment(postId: String, commentId: String):CommentEntity

    /**
     * 评论分页
     */
    fun commentPage()

    /**
     * 评论点赞
     */
    fun commentRaise()

    /**
     * 删除评论
     */
    fun commentDelete()
}