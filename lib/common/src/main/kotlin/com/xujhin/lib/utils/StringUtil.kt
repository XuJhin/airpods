package com.xujhin.lib.utils

import org.springframework.util.AntPathMatcher




class StringUtil {
    companion object {
        fun matches(str: String, whites: List<String>): Boolean {
            if (str.isNullOrEmpty() || whites.isNullOrEmpty()) {
                return false
            }
            for (pattern in whites) {
                if (isMatch(pattern, str)) {
                    return true
                }
            }
            return false
        }

        private fun isMatch(pattern: String, url: String): Boolean {
            val matcher = AntPathMatcher()
            return matcher.match(pattern, url)
        }

    }
}