package com.xujhin.lib.error

enum class ErrEnum(code: Int, msg: String) {
    Busy(400, "前方拥堵, 请稍候重试~"),
    Forbid(400, "您已被禁言"),
    Exist(400, "重复操作"),
    Black(400, "您已被永久禁言，可联系客服进行申诉处理"),
    Idempotent(400, "重复操作"),

    InvalidUser(400, "无效用户"),

    InvalidSession(401, "登录过期"),
    VERIFICATION_CODE_INVALID(250, "验证码无效"),

}