package me.a632079.ctalk.controller;

import cn.hutool.core.lang.Snowflake;

import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.service.UserService;

import org.springframework.cglib.transform.ClassTransformerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Resource
    private ConcurrentHashMap<Long, UserInfo> userInfoMap;

    @GetMapping("/foo")
    public String pp() {
        return "foo";
    }

    @GetMapping("/list")
    public List<Integer> list() {
        return List.of(1, 2, 3);
    }

    @GetMapping("/error")
    public void error() {
        throw CTalkExceptionFactory.bizException(CTalkErrorCode.TEST_ERROR);
    }

    @GetMapping("/online")
    public Map<Long, UserInfo> getOnline() {
        return userInfoMap;
    }
}
