package me.a632079.ctalk.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: ExchangeAndQueueConfig
 * @description: ExchangeAndQueueConfig - 交换机和队列配置
 * @version: v1.0.0
 * @author: haoduor
 */

@Component
public class ExchangeAndQueueConfig {

    @Resource
    private RabbitAdmin rabbitAdmin;

    /**
     * 生成交换机 绑定队列
     *
     * @param exchange   交换机
     * @param routingKey 路由
     * @param queueName  队列
     */
    public void createDirectBindQueue(String exchange, String routingKey, String queueName) {
        DirectExchange directExchange = new DirectExchange(exchange, false, false);
        Queue queue = new Queue(queueName, false, true, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange)
                                        .with(routingKey);
        //创建
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareBinding(binding);
    }

    public void removeDirectBindQueue(String exchange, String routingKey, String queueName) {
        DirectExchange directExchange = new DirectExchange(exchange, false, false);
        Queue queue = new Queue(queueName, false, true, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange)
                                        .with(routingKey);
        //删除
        rabbitAdmin.removeBinding(binding);
        rabbitAdmin.deleteExchange(exchange);
    }

    public void createFanoutBindQueue(String exchange, String queueName) {
        FanoutExchange directExchange = new FanoutExchange(exchange, false, false);
        Queue queue = new Queue(queueName, false, true, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange);

        //创建
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareBinding(binding);
    }

    public void removeFanoutBindQueue(String exchange, String queueName) {
        FanoutExchange directExchange = new FanoutExchange(exchange, false, false);
        Queue queue = new Queue(queueName, false, true, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange);

        //删除
        rabbitAdmin.deleteExchange(exchange);
        rabbitAdmin.removeBinding(binding);
    }

    public void createPrivateMessageBind(Long uid) {
        this.createDirectBindQueue("message.private", "user." + uid, "user." + uid);
    }

    public void removePrivateMessageBind(Long uid) {
        this.removeDirectBindQueue("message.private", "user." + uid, "user." + uid);
    }

    public void createGroupMessageBind(Long gid, Long uid) {
        this.createFanoutBindQueue("message.group." + gid, "user." + uid);
    }

    public void removeGroupMessageBind(Long gid, Long uid) {
        this.removeFanoutBindQueue("message.group." + gid, "user." + uid);
    }
}
