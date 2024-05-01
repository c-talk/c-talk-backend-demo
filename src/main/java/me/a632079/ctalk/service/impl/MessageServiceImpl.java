package me.a632079.ctalk.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.enums.ChatType;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.vo.MessageForm;
import me.a632079.ctalk.vo.MessageHistoryForm;
import me.a632079.ctalk.vo.PageVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
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
    private MongoTemplate template;

    @Resource
    private ConcurrentHashMap<Long, UserInfo> userInfoMap;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public Message addPrivateMessage(MessageForm form) throws JsonProcessingException {
        Message message = mapperFacade.map(form, Message.class);
        message.setChatType(ChatType.Private);
        message.genIdent();

        // TODO 双方互为好友检查

        template.insert(message, message.getDocumentName());

        Long uid = message.getReceiver();

        UserInfo info = userInfoMap.getOrDefault(uid, null);

        if (Objects.nonNull(info)) {
            info.getClient()
                .sendEvent("private", objectMapper.writeValueAsString(message));
        } else {
            amqpTemplate.convertAndSend("user." + uid, objectMapper.writeValueAsString(message));
        }

        return message;
    }

    @Override
    public Message addGroupMessage(MessageForm form) throws JsonProcessingException {
        Message message = mapperFacade.map(form, Message.class);
        message.setChatType(ChatType.Group);

        // TODO 在群组中检查
        template.insert(message, message.getDocumentName());

        amqpTemplate.convertAndSend("message.group." + message.getReceiver(), objectMapper.writeValueAsString(message));

        return message;
    }

    @Override
    public List<Message> getFirstPrivateMessageByFriend(List<Long> sender, Long receiver) {
        // TODO 聚合存在问题
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

        AggregationResults<Message> results = template.aggregate(Aggregation.newAggregation(operations), message.getDocumentName(), Message.class);

        return results.getMappedResults();
    }

    @Override
    public List<Message> getPrivateMessage(Long senderId, Long receiverId) {
        Message message = new Message();
        message.setChatType(ChatType.Private);
        message.setSender(senderId);
        message.setReceiver(receiverId);

        Query query = new Query();

        return template.find(
                query.with(Sort.by(Sort.Direction.DESC, "createTime")),
                Message.class,
                message.getDocumentName());
    }

    @Override
    public List<Message> getGroupMessage(Long id) {
        return null;
    }

    @Override
    public PageVo<Message> pagePrivateMessage(MessageHistoryForm form) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(form.getPageNum() - 1, form.getPageSize(), sort);

        Message message = mapperFacade.map(form, Message.class);
        message.setChatType(ChatType.Private);

        Long count = template.count(query, Message.class, message.getDocumentName());

        List<Message> result = template.find(query.with(pageRequest), Message.class, message.getDocumentName());

        return PageVo.of(result, form, count);
    }

    @Override
    public PageVo<Message> pageGroupMessage(MessageHistoryForm form) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(form.getPageNum() - 1, form.getPageSize(), sort);

        Message message = mapperFacade.map(form, Message.class);
        message.setChatType(ChatType.Group);

        Long count = template.count(query, Message.class, message.getDocumentName());

        List<Message> result = template.find(query.with(pageRequest), Message.class, message.getDocumentName());

        return PageVo.of(result, form, count);
    }
}
