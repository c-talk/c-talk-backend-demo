package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.StrUtil;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.Friend;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.repository.FriendRepository;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.MessageUtil;
import me.a632079.ctalk.util.UserInfoUtil;
import me.a632079.ctalk.vo.FriendSearchForm;
import me.a632079.ctalk.vo.FriendVo;
import org.simpleframework.xml.Path;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.plaf.ListUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private MongoTemplate template;

    @Resource
    private FriendRepository repository;

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService;

    @Resource
    private MapperFacade mapperFacade;


    @PostMapping("/list/{id}")
    public List<FriendVo> list(@RequestBody FriendSearchForm form, @PathVariable Long id) {
        MatchOperation matchId = Aggregation.match(Criteria.where("uid")
                                                           .is(id));

        // 创建 $lookup
        LookupOperation lookupOperation = LookupOperation.newLookup()
                                                         .from("user")
                                                         .localField("friendId")
                                                         .foreignField("_id")
                                                         .as("friend");

        UnwindOperation unwindOperation = Aggregation.unwind("friend", true);

        Criteria criteria = new Criteria();

        if (StrUtil.isNotBlank(form.getEmail())) {
            criteria.orOperator(
                    Criteria.where("friend.email")
                            .regex(form.getEmail())
            );
        }

        if (StrUtil.isNotBlank(form.getNickName())) {
            criteria.orOperator(
                    Criteria.where("friend.nickName")
                            .regex(form.getNickName())
            );
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        // 聚合查询
        Aggregation aggregation = Aggregation.newAggregation(
                matchId,
                lookupOperation,
                unwindOperation,
                matchOperation
        );

        // 执行聚合查询
        AggregationResults<FriendVo> results = template.aggregate(aggregation, "friend", FriendVo.class);
        return results.getMappedResults();
    }

    @GetMapping("/list/{id}/with/message")
    public List<FriendVo> listWithMessage(@PathVariable Long id) {
        List<Friend> friends = repository.findAllByUid(id);

        if (friends.isEmpty()) {
            return new ArrayList<>();
        }

        List<Message> messages = messageService.getFirstPrivateMessageByFriend(CollUtil.map(friends, Friend::getFriendId, true), StpUtil.getLoginIdAsLong());
        Map<String, Message> messageMap = messages.stream()
                                                  .collect(Collectors.toMap(Message::getIdentify, e -> e, (a, b) -> a));

        return mapperFacade.mapAsList(friends, FriendVo.class)
                           .stream()
                           .peek(e -> e.setMessage(messageMap.getOrDefault(MessageUtil.hash(e), null)))
                           .collect(Collectors.toList());
    }

    @GetMapping("/page/{id}")
    public Page<Friend> page(@PathVariable Long id) {
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

        // TODO 需要判断好友是否存在
        Friend friend = Friend.builder()
                              .friendId(id)
                              .uid(UserInfoUtil.getId())
                              .build();

        repository.insert(friend);

        friend = Friend.builder()
                       .friendId(UserInfoUtil.getId())
                       .uid(id)
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
