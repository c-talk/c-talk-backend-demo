package me.a632079.ctalk.vo;

import lombok.Data;

/**
 * @className: LoginForm
 * @description: LoginForm - 登录表单
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class LoginForm {
    private String email;
    private String password;

    // TODO 登录验证码
    private String code;
}
