package me.a632079.ctalk.enums;

import lombok.AllArgsConstructor;
import me.a632079.ctalk.exception.IErrorCode;

/**
 * @className: CTalkErrorCode
 * @description: CTalkErrorCode - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@AllArgsConstructor
public enum CTalkErrorCode implements IErrorCode {

    PASSWORD_NOT_PASS("101", "两次密码输入不一致"),
    EMAIl("102", "邮件已被占用"),

    EMAIL_OR_PASSWORD_WRONG("201", "邮箱或密码错误"),

    TEST_ERROR("999", "测试错误");

    private final String code;

    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
