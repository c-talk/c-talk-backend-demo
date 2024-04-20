package me.a632079.ctalk.vo;

import lombok.Data;

/**
 * @className: RegisterForm
 * @description: RegisterForm - 注册表单
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class RegisterForm {
    private String nickName;
    private String password;
    private String rePassword;
    private String email;
}
