package me.a632079.ctalk.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import me.a632079.ctalk.po.UserInfo;

import java.io.IOException;

/**
 * @className: PrivateMessageConsumer
 * @description: PrivateMessageConsumer - 私人消息处理
 * @version: v1.0.0
 * @author: haoduor
 */


public class PrivateMessageConsumer extends DefaultConsumer {

    private UserInfo userInfo;

    public PrivateMessageConsumer(Channel channel, UserInfo userInfo) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {
        String content = new String(body);

        userInfo.getClient()
                .sendEvent("private", content);
    }
}
