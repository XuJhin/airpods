package com.xujhin.user.service

import com.xujhin.lib.dto.User

interface IUserService {

    fun register()
    fun login(loginData: Map<String, Any?>):User
    fun getUserInfo(userId: String):User
    fun updateUserInfo(userId: String,updateData:Map<String, Any?>):User
    fun logout(userId: String)

}