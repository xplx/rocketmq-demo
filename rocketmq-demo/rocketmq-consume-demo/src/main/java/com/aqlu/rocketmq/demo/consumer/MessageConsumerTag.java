package com.aqlu.rocketmq.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * MessageExtConsumer Created by aqlu on 2017/11/16.
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "message-ext-topic", selectorExpression = "tag0",
        consumerGroup = "${spring.application.name}-message-ext0-consumer")
public class MessageConsumerTag implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        log.info("------- MessageExtConsumer received message, msgId:{}, body:{} ",
                message.getMsgId(), new String(message.getBody()));
    }
}
