package me.a632079.ctalk;

import me.a632079.ctalk.config.ExchangeAndQueueConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

}
