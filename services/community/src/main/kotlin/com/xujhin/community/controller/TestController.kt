package com.xujhin.community.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.xujhin.community.service.impl.CommentService

@RestController
@RequestMapping("/community")
class TestController {
    @Autowired
    private lateinit var commentService: CommentService

    @GetMapping("/test/greet")
    fun greet(): String {
        return commentService.hello()
    }
}