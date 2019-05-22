package com.aqlu.rocketmq.demo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.apache.rocketmq.spring.starter.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * StringConsumer Created by aqlu on 2017/11/16.
 * 主题使用的是消费者，生产者向该用户发送消息
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "string-topic", selectorExpress = "message1", consumerGroup = "string_consumer1")
public class StringConsumer1 implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // set consumer consume message from now
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        defaultMQPushConsumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            Map<String, String> properties = messageExt.getProperties();
            String tags = properties.get("TAGS");
            String body = new String(messageExt.getBody());
            if ("message1".equals(tags)) {
                log.info("------- StringConsumer1 received: {}", body);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }
}
