package me.a632079.ctalk.controller;

import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.vo.MessageForm;
import me.a632079.ctalk.vo.MessageHistoryForm;
import me.a632079.ctalk.vo.PageVo;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/history/private")
    public PageVo<Message> pagePrivateMessage(@RequestBody MessageHistoryForm form) {
        return messageService.pagePrivateMessage(form);
    }

    @PostMapping("/history/group")
    public PageVo<Message> pageGroupMessage(@RequestBody MessageHistoryForm form) {
        return messageService.pageGroupMessage(form);
    }
}
