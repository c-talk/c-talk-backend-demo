package me.a632079.ctalk.controller;

import cn.hutool.core.lang.Snowflake;

import me.a632079.ctalk.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @GetMapping("/list")
    public List<Integer> list() {
        return List.of(1,2,3);
    }
}
