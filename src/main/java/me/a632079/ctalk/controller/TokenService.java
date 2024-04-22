package me.a632079.ctalk.controller;

import me.a632079.ctalk.po.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TokenService
 * @description: TokenService - 令牌
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
@RequestMapping("/token")
public class TokenService {

    @GetMapping("/get")
    public Token getToken() {
        return null;
    }
}
