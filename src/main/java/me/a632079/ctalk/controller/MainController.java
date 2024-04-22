package me.a632079.ctalk.controller;

import me.a632079.ctalk.vo.LoginForm;
import me.a632079.ctalk.vo.RegisterForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: MainController
 * @description: MainController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
public class MainController {

    @RequestMapping("/login")
    public void login(@RequestBody LoginForm loginForm) {

    }

    @RequestMapping("/register")
    public void register(@RequestBody RegisterForm registerForm) {

    }

}
