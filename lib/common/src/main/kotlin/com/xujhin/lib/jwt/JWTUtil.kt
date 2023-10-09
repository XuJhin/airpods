package com.xujhin.lib.jwt

import com.xujhin.lib.config.properties.JwtProperties
import com.xujhin.lib.error.BizException
import com.xujhin.lib.error.ErrEnum
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
open class JWTUtil {
    @Autowired
    private lateinit var jwtProperties: JwtProperties

    private val key:Key by lazy {
        Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)
    }

    //根据用户id生成sessionToken
    fun createToken(id: String): String {
        return Jwts
            .builder()
            .claim(jwtProperties.userParamName, id)
            .claim("role", "user")
            .signWith(SignatureAlgorithm.HS256,jwtProperties.secret)
            .compact()
    }


    //根据用户id生成sessionToken
    fun createMiniToken(id: String): String {
        return Jwts
            .builder()
            .claim(jwtProperties.userParamName, id)
            .claim("role", "miniuser")
            .setIssuedAt(Date())
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)))
            .compact()
    }


    //校验sessionToken并返回id
    fun verifyToken(jwt: String?): String {
        val id = try {
            val claimsJws: Jws<Claims> = Jwts
                .parser()
                .setSigningKey(jwtProperties.secret)
                .build()
                .parseClaimsJws(jwt)
            //OK, we can trust this JWT
            claimsJws.payload[jwtProperties.userParamName].toString()
        } catch (e: JwtException) {
            e.printStackTrace()
            throw BizException(ErrEnum.InvalidSession)
            //don't trust the JWT!
        }
        return id
    }

}