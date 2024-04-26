package me.a632079.ctalk.service;


import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.vo.MessageForm;

import java.util.List;

public interface MessageService {
    void addPrivateMessage(MessageForm form);

    void addGroupMessage(MessageForm form);

    List<Message> getPrivateMessage(Long senderId, Long receiverId);

    List<Message> getGroupMessage(Long id);
}
