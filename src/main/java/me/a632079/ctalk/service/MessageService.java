package me.a632079.ctalk.service;

import me.a632079.ctalk.po.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MessageService extends MongoRepository<User> {

}
