package com.xujhin.lib.core.domain

class R<T> {

    var code: Int = 200
    var msg: String = ""
    var data: T? = null

    companion object {
        fun <T> fail(code: Int, toString: String): R<T> {
            return restResult(null, code, toString)
        }

        private fun <T> restResult(data: T?, code: Int, msg: String): R<T> {
            val apiResult: R<T> = R()
            apiResult.apply {
                this.code=code
                this.msg=msg
                this.data=data
            }

            return apiResult
        }

        const val FAIL = 400
    }
}