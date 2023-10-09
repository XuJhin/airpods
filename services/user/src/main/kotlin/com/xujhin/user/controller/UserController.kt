package com.xujhin.user.controller

import com.xujhin.lib.entity.BaseResp
import com.xujhin.user.service.impl.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/u")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/user/{userId}")
    fun userInfo(@PathVariable userId: String): BaseResp {
        return BaseResp(userService.getUserInfo(userId = userId))
    }

    @PostMapping("/login")
    fun login(@RequestBody authDataMap: Map<String, Any>): BaseResp {
        return BaseResp(userService.login(authDataMap))
    }

    @PostMapping("/update")
    fun update(userId: String, @RequestBody obj: Map<String, Any>): BaseResp {
        return BaseResp(userService.updateUserInfo(userId = userId, obj))
    }

    @GetMapping("/sms")
    fun sms(id: String, code: String): BaseResp {
        userService.addCaptchaCode(id, code)
        return BaseResp(null)
    }

    @GetMapping("/logout")
    fun logout() {
    }

    @GetMapping("/greet")
    fun greet(): String {
        return userService.greet()
    }

}