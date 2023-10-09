package com.xujhin.user.dao

import com.xujhin.lib.db.BaseDao
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
open class UserDao:BaseDao() {
    override fun <T : Any> save(obj: T): T {
        return super.save(obj)
    }
}