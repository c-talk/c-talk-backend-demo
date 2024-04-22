package me.a632079.ctalk.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @className: MessageEventHandler
 * @description: MessageEventHandler - 事件消息接收器
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@Component
public class MessageEventHandler {

    /**
     * 成功创建连接时调用
     *
     * @param client 用户连接
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {

    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {

    }

}
