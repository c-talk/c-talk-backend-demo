package me.a632079.ctalk.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: TestController
 * @description: TestController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserService userService;

    @Resource
    private Snowflake snowflake;

    @GetMapping("/pp")
    public String pp() {
        return "foo";
    }

    @GetMapping("/user")
    public void user100() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            User user = User.builder()
                             .id(snowflake.nextId())
                             .password(RandomUtil.randomString(64))
                             .avatar(RandomUtil.randomString(20))
                             .email(RandomUtil.randomString(10) + "@gmail.com")
                             .verify(false)
                             .nickName(RandomUtil.randomString(5))
                             .build();
            userList.add(user);
        }

        Flux<User> userFlux = userService.saveAll(userList);
        userFlux.subscribe().dispose();
    }

    @GetMapping("/find")
    public User find() {
        User user = User.builder()
                         .id(1781144015178174492L)
                         .build();

        Flux<User> all = userService.findAll(Example.of(user));

        return all.blockFirst();
    }
}
