package com.xujhin.lib.dto

import com.xujhin.lib.entity.BaseEntity

class User : BaseEntity() {
    var version: String = ""
    var sessionToken: String = ""
    var lastLogin: Long = 0
    var nickname: String = ""
    var avatarUrl: String = ""
}