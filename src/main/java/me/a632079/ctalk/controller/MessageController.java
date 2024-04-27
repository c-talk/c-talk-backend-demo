package me.a632079.ctalk.controller;

import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.vo.MessageForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: MessageController
 * @description: MessageController - 消息
 * @version: v1.0.0
 * @author: haoduor
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @PostMapping("/send/private")
    public Message sendPrivateMessage(@RequestBody MessageForm form) {
        return messageService.addPrivateMessage(form);
    }

    @PostMapping("/send/group")
    public Message sendGroupMessage(@RequestBody MessageForm form) {
        return messageService.addGroupMessage(form);
    }
}
