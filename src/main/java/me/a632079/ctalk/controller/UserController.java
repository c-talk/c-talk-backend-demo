package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
import me.a632079.ctalk.vo.*;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private Argon2Util argon2Util;

    @GetMapping("/list")
    public List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping("/page")
    public PageVo<UserVo> page(@RequestBody @Validated UserPageForm form) {
        return userService.pageUser(form);
    }

    @PostMapping("/set")
    public void set(UserSetForm form) {
        User user = mapperFacade.map(form, User.class);
        user.setId(StpUtil.getLoginIdAsLong());

        userRepository.save(user);
    }

    @PostMapping("/changePassword")
    public void changePassword(ChangePasswordForm form) {
        Optional<User> user = userRepository.findById(StpUtil.getLoginIdAsLong());
        if (user.isEmpty()) {
            return;
        }

        User u = user.get();

        if (!argon2Util.verify(u, form.getOriPassword())) {
            //TODO 异常
            return;
        }

        if (!form.getPassword()
                 .equals(form.getRePassword())) {
            return;
        }

        u.setPassword(argon2Util.hash(form.getPassword()));
        userRepository.save(u);

        StpUtil.logout(u.getId());
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Long id) {
        return userRepository.findById(id)
                             .orElse(null);
    }
}
