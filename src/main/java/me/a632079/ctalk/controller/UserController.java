package me.a632079.ctalk.controller;

import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: UserController
 * @description: UserController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @GetMapping("/list")
    public List<User> list() {
        userRepository.findAll();
        return userRepository.findAll();
    }

    @GetMapping("/page")
    public void page() {
    }

    @PostMapping("/set/{id}")
    public void set() {
    }

    @GetMapping("/get/{id}")
    public void get() {
    }
}
