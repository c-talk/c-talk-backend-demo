package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: FriendRepository
 * @description: FriendRepository - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@Repository
public interface FriendRepository extends MongoRepository<Friend, Long> {
}
