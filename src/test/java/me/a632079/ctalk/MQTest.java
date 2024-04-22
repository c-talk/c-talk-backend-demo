package me.a632079.ctalk;

import com.rabbitmq.client.*;
import me.a632079.ctalk.config.ExchangeAndQueueConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @className: MQTest
 * @description: MQTest - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@SpringBootTest
public class MQTest {

    @Resource
    private ExchangeAndQueueConfig config;

    @Test
    public void createDynamicQueue() {
        config.createDirectBindQueue("exchange.test", "bibi", "bobo");
    }

    @Test
    public void testCreate() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://user:password@192.168.63.187:5672");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        System.out.println(channel.isOpen());

        channel.basicConsume("sms", true, new SimpleConsumer(channel));

        TimeUnit.SECONDS.sleep(20);
        System.out.println(channel.isOpen());

        channel.close();
        connection.close();
    }

    class SimpleConsumer extends DefaultConsumer {

        public SimpleConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
            System.out.println(consumerTag);
            System.out.println("-----收到消息了-----------");
            System.out.println("消息属性为：" + properties);
            System.out.println("消息内容为：" + new String(body));

            throw new NullPointerException("空指针异常");
        }
    }
}
