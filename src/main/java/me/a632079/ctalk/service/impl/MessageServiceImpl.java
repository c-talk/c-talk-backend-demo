package me.a632079.ctalk.service.impl;

import com.mongodb.client.model.Aggregates;
import com.mongodb.internal.operation.AggregateOperation;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.enums.ChatType;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.service.TokenService;
import me.a632079.ctalk.vo.MessageForm;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ConcurrentHashMap<Long, UserInfo> userInfoMap;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private MapperFacade mapperFacade;

    @Override
    public void addPrivateMessage(MessageForm form) {
        Message message = mapperFacade.map(form, Message.class);
        message.setChatType(ChatType.Private);
        message.genIdent();

        mongoTemplate.insert(message, message.getDocumentName());

        Long uid = message.getReceiver();

        UserInfo info = userInfoMap.getOrDefault(uid, null);

        if (Objects.nonNull(info)) {
            info.getClient()
                .sendEvent("private", message);
        } else {
            amqpTemplate.convertAndSend("user." + uid, message);
        }
    }

    @Override
    public void addGroupMessage(MessageForm form) {
        Message message = mapperFacade.map(form, Message.class);

    }

    @Override
    public List<Message> getFirstPrivateMessageByFriend(List<Long> sender, Long receiver) {
        List<AggregationOperation> operations = new ArrayList<>();
        Message message = new Message();
        for (Long s : sender) {
            message.setSender(s);
            message.setReceiver(receiver);
            message.setChatType(ChatType.Private);
            operations.add(UnionWithOperation.unionWith(message.getDocumentName()));
        }

        operations.add(Aggregation.sort(Sort.Direction.DESC, "createTime"));

        Aggregation.group("identify")
                   .first("$$ROOT");

        AggregationResults<Message> results = mongoTemplate.aggregate(Aggregation.newAggregation(operations), message.getDocumentName(), Message.class);

        return results.getMappedResults();
    }

    @Override
    public List<Message> getPrivateMessage(Long senderId, Long receiverId) {
        Message message = new Message();
        message.setChatType(ChatType.Private);
        message.setSender(senderId);
        message.setReceiver(receiverId);

        Query query = new Query();

        return mongoTemplate.find(
                query.with(Sort.by(Sort.Direction.DESC, "createTime")),
                Message.class,
                message.getDocumentName());
    }

    @Override
    public List<Message> getGroupMessage(Long id) {
        return null;
    }
}
