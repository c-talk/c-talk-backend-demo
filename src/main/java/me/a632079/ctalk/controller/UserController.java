package me.a632079.ctalk.controller;

import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.vo.PageVo;
import me.a632079.ctalk.vo.UserPageForm;
import me.a632079.ctalk.vo.UserVo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
        return userRepository.findAll();
    }

    @PostMapping("/page")
    public PageVo<UserVo> page(@RequestBody UserPageForm form) {
        return userService.pageUser(form);
    }

    @PostMapping("/set/{id}")
    public void set() {
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Long id) {
        return userRepository.findById(id)
                             .orElse(null);
    }
}
