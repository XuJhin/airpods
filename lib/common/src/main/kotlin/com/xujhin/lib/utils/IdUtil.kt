package com.xujhin.lib.utils

import org.apache.commons.lang3.StringUtils
import java.security.SecureRandom

class IdUtil {
    companion object {
        private val DIGITS = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            '*',
            '-'
        )

        fun genId(): String {
            return try {
                val i = SecureRandom.getInstance("SHA1PRNG").nextInt(62 * 62 * 62 - 1)
                val time: String =
                    StringUtils.leftPad(IdUtil.To62String(System.currentTimeMillis()), 7, '0') // 前 7 位是时间戳，精确到毫秒。
                val random: String = StringUtils.leftPad(IdUtil.To62String(i.toLong()), 3, '0') // 后三位是随机数
                time + random
            } catch (e: Exception) {
                throw RuntimeException("ID 生成失败")
            }
        }


        /**
         * 10 进制转成 62 进制
         */
        private fun To62String(timeMillis: Long): String {
            var timeMillis = timeMillis
            val mask = 62
            val bufLength = 11
            var charPos = bufLength
            val buf = CharArray(bufLength)
            do {
                buf[--charPos] = IdUtil.DIGITS[(timeMillis % mask).toInt()]
                timeMillis /= mask
            } while (timeMillis > 0)
            return String(buf, charPos, bufLength - charPos)
        }

    }
}