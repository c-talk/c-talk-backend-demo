package me.a632079.ctalk.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.db.sql.Wrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
import me.a632079.ctalk.vo.PageVo;
import me.a632079.ctalk.vo.RegisterForm;
import me.a632079.ctalk.vo.UserPageForm;
import me.a632079.ctalk.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.util.QueryExecutionConverters;
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

    private final MapperFacade mapperFacade;

    public static final String DOCUMENT_NAME = "user";

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
        return userRepository.findOneByEmailEquals(email);
    }

    @Override
    public PageVo<UserVo> pageUser(UserPageForm form) {
        Pageable pageable = Pageable.ofSize(form.getPageSize())
                                    .withPage(form.getPageNum());

        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                                               .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains());
        User user = mapperFacade.map(form, User.class);
        Page<User> page = userRepository.findAll(pageable);
        Page<UserVo> userVoPage = page.map(e -> mapperFacade.map(e, UserVo.class));
        return PageVo.of(userVoPage);
    }

    @Override
    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }

}
