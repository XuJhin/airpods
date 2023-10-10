package com.xujhin.lib.jwt

import com.xujhin.lib.constant.SecurityConstants
import com.xujhin.lib.constant.TokenConstants
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component


/**
 * Jwt工具类
 *
 * @author ruoyi
 */
@Component
class JwtManager {
    var secret: String = TokenConstants.SECRET

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    fun createToken(claims: Map<String?, Any?>?): String {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    fun parseToken(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).body
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    fun getUserKey(token: String?): String {
        val claims = parseToken(token)
        return getValue(claims, SecurityConstants.USER_KEY)
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    fun getUserKey(claims: Claims): String {
        return getValue(claims, SecurityConstants.USER_KEY)
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    fun getUserID(token: String?): String {
        val claims = parseToken(token)
        return getValue(claims, SecurityConstants.DETAILS_USER_ID)
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    fun getUserID(claims: Claims): String {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID)
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    fun getUserName(token: String?): String {
        val claims = parseToken(token)
        return getValue(claims, SecurityConstants.DETAILS_USERNAME)
    }

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    fun getUserName(claims: Claims): String {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME)
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key 键
     * @return 值
     */
    fun getValue(claims: Claims, key: String): String {
        return claims[key] as String
    }
}

