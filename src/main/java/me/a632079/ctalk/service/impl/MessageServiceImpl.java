package me.a632079.ctalk.service.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl {
    @Resource
    private MongoTemplate mongoTemplate;
}
