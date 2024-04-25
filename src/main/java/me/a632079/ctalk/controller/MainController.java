package me.a632079.ctalk.controller;

import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
import me.a632079.ctalk.vo.LoginForm;
import me.a632079.ctalk.vo.RegisterForm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @className: MainController
 * @description: MainController
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
public class MainController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    @Resource
    private Argon2Util argon2Util;

    @PostMapping("/login")
    public User login(@RequestBody LoginForm form, HttpSession session) {
        // TODO 需要验证码校验
        User user = userService.getUserByEmail(form.getEmail());

        // 用户不存在
        if (Objects.isNull(user)) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.EMAIL_OR_PASSWORD_WRONG);
        }

        //密码校验
        if (!argon2Util.verify(user.getPassword(), form.getPassword())) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.EMAIL_OR_PASSWORD_WRONG);
        }

        //id写入session
        session.setAttribute("id", user.getId());

        return user;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterForm form) {
        // 2次密码
        if (!form.getPassword()
                 .equals(form.getRePassword())) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.PASSWORD_NOT_PASS);
        }

        // 邮箱唯一
        if (Objects.nonNull(userService.getUserByEmail(form.getEmail()))) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.EMAIl);
        }

        // 创建用户
        userService.addUser(form);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
