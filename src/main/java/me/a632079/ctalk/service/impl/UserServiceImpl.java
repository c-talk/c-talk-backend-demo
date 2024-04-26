package me.a632079.ctalk.service.impl;

import cn.hutool.core.lang.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
import me.a632079.ctalk.vo.RegisterForm;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @className: UserService
 * @description: UserService - 用户功能
 * @version: v1.0.0
 * @author: haoduor
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate template;

    private final UserRepository userRepository;

    private final Argon2Util argon2Util;

    private final Snowflake snowflake;

    @Override
    public User addUser(RegisterForm form) {
        User user = User.builder()
                        .avatar("")
                        .email(form.getEmail())
                        .password(argon2Util.hash(form.getPassword()))
                        .nickName(form.getNickName())
                        // TODO 邮箱校验
                        .verify(true)
                        .build();

        user.setId(snowflake.nextId());

        return userRepository.insert(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                             .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = User.builder()
                        .email(email)
                        .build();
        return userRepository.findOne(Example.of(user))
                             .orElse(null);
    }

    @Override
    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }
}
