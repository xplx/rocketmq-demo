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

import java.util.Map;

/**
 * StringConsumer Created by aqlu on 2017/11/16.
 * 主题使用的是消费者，生产者向该用户发送消息
 * selectorExpress = "message2":选择消费指定的消息条件
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "string-topic", selectorExpress = "message2", consumerGroup = "string_consumer2")
public class StringConsumer2 implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
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
            if ("message2".equals(tags)) {
                log.info("------- StringConsumer2 received: {}", body);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }
}
