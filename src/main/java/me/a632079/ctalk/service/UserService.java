package me.a632079.ctalk.service;

import me.a632079.ctalk.po.User;
import me.a632079.ctalk.vo.RegisterForm;

/**
 * @className: UserService
 * @description: UserService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


public interface UserService {
    User addUser(RegisterForm form);

    User getUserById(Long id);

    User getUserByEmail(String email);

    boolean exist(Long id);
}
