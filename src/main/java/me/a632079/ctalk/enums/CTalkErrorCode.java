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

    TEST_ERROR("99", "测试错误");

    private final String code;

    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
