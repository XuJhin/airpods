package com.xujhin.community.controller

import com.xujhin.community.entity.CommentEntity
import com.xujhin.community.service.impl.CommentService
import com.xujhin.community.service.impl.ExtUserService
import com.xujhin.lib.entity.BaseResp
import com.xujhin.lib.error.BizException
import com.xujhin.lib.error.ErrEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/community/")
class CommentController {
    @Autowired
    lateinit var commentService: CommentService

    @Autowired
    lateinit var extUserService: ExtUserService


    @PostMapping("/save")
    fun commentPost(@RequestBody @Validated obj: CommentEntity): BaseResp {
        checkUserState(obj)
        return BaseResp(commentService.commentPost(obj))
    }

    private fun checkUserState(obj: CommentEntity) {
        val state = extUserService.checkBlack(obj.userId, obj.deviceId)
        if (state.isNullOrEmpty()) {
            throw BizException(ErrEnum.InvalidUser)
        } else if (state == "forbid") {
            throw BizException(ErrEnum.Forbid)
        } else if (state == "black") {
            throw BizException(ErrEnum.Black)
        }
    }
}