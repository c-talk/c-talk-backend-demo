package me.a632079.ctalk.service;


import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.vo.MessageForm;
import me.a632079.ctalk.vo.MessageHistoryForm;
import me.a632079.ctalk.vo.PageVo;

import java.util.List;

public interface MessageService {
    Message addPrivateMessage(MessageForm form);

    Message addGroupMessage(MessageForm form);

    List<Message> getFirstPrivateMessageByFriend(List<Long> sender, Long receiver);

    List<Message> getPrivateMessage(Long senderId, Long receiverId);

    List<Message> getGroupMessage(Long id);

    PageVo<Message> pagePrivateMessage(MessageHistoryForm form);

    PageVo<Message> pageGroupMessage(MessageHistoryForm form);
}
