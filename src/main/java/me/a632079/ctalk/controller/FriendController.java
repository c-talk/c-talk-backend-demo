package me.a632079.ctalk.controller;

import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.Friend;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.repository.FriendRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.UserInfoUtil;
import org.simpleframework.xml.Path;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: FriendController
 * @description: FriendController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private FriendRepository repository;

    @Resource
    private UserService userService;

    @GetMapping("/list/{id}")
    public List<Friend> list(@PathVariable Long id) {
        Friend friend = new Friend();
        friend.setUid(id);
        return repository.findAll(Example.of(friend));
    }

    @GetMapping("/page/{id}")
    public Page<Friend> page() {
        return null;
    }

    /**
     * @param id 好友uid
     */
    @PostMapping("/add")
    public void add(@RequestParam Long id) {
        if (!userService.exist(id)) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.TODO);
        }

        Friend friend = Friend.builder()
                              .friendId(id)
                              .uid(UserInfoUtil.getId())
                              .build();

        repository.insert(friend);
    }

    /**
     * @param relationId 关系id
     */
    @PostMapping("/remove")
    public void remove(@RequestParam Long relationId) {
        repository.deleteById(relationId);
    }
}
