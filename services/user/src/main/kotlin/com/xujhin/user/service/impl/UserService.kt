package com.xujhin.user.service.impl

import com.xujhin.lib.jwt.JWTUtil
import com.xujhin.lib.config.LocalServiceConfigProperties
import com.xujhin.lib.dto.User
import com.xujhin.lib.error.BizException
import com.xujhin.lib.error.ErrEnum
import com.xujhin.lib.utils.IdUtil
import com.xujhin.user.common.AbsUserService
import com.xujhin.user.dao.UserDao
import com.xujhin.user.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service

@Service
class UserService : AbsUserService(), IUserService {

    @Autowired
    lateinit var userDao: UserDao

    @Autowired
    lateinit var jwtUtil: JWTUtil

    @Autowired
    lateinit var userProperties: LocalServiceConfigProperties


    fun greet(): String {
        return "hello"
    }

    override fun register() {
        TODO("Not yet implemented")
    }

    override fun login(loginData: Map<String, Any?>): User {
        val version: String = (loginData["app"] ?: "") as String
        val authData: HashMap<String, Any?>? = loginData["authData"] as HashMap<String, Any?>?
        val smsData: HashMap<String, Any?> = loginData["sms"] as HashMap<String, Any?>
        //===========短信登录=======
        if ((smsData["id"] as String?).isNullOrEmpty()) {
            throw BizException(ErrEnum.Exist)
        }
        val phoneNumber = (smsData["id"] as String).replace(" ", "")
        val zone: String = (smsData["zone"] as String?) ?: ""
        val mobile = (smsData["mobile"] as String?) ?: ""
        val code = (smsData["code"] as String?) ?: ""

        if (!checkVersion(version, code)) {
            throw BizException(ErrEnum.VERIFICATION_CODE_INVALID)
        }


        val user: User? = userDao.findOne(Criteria.where("mobilePhoneNumber").`is`(phoneNumber))

        //新用户创建
        if (user == null) {
            val userDto = User().apply {
                id = IdUtil.genId()
                nickname = if (this.nickname.isNullOrEmpty()) {
                    "盯友${id.substring(0, 5)}"
                } else {
                    this.nickname
                }
                this.version = version
                this.createAt = System.currentTimeMillis()
                this.lastLogin = System.currentTimeMillis()
                this.sessionToken = jwtUtil.createToken(this.id)
            }
            return userDao.save(userDto)
        }
        user.apply {
            lastLogin = System.currentTimeMillis()
        }
        //更新已有用户数据
        return user

    }

    override fun getUserInfo(userId: String): User {
        val user = userDao.findOne(User::class.java, id = userId)
        if (user == null)
            throw BizException(ErrEnum.InvalidUser)
        else return user
    }

    override fun updateUserInfo(userId: String, updateData: Map<String, Any?>): User {
        return User()
    }

    override fun logout(userId: String) {
    }

    fun addCaptchaCode(id: String, code: String) {

    }


    /**
     * 测试环境和版本version 校验729514
     */

    private fun checkVersion(version: String, code: String): Boolean {
        if (userProperties.env.equals("dev", ignoreCase = true) && code == "729514") {
            return true
        }

        return false
    }
}