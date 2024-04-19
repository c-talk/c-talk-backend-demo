package me.a632079.ctalk.service;

import me.a632079.ctalk.po.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

/**
 * @className: UserService
 * @description: UserService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@Service
public interface UserService extends ReactiveMongoRepository<User, Long> {
}
