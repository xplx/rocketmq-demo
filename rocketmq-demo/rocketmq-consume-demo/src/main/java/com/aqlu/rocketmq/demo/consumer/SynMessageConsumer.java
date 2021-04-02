package com.aqlu.rocketmq.demo.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Service;

/**
 * @author xiaopeng
 * @date 2021年03月29日 16:13
 * @description 发送同步消息
 */
@Service
@RocketMQMessageListener(topic = "stringRequestTopic", consumerGroup = "stringRequestConsumer")
public class SynMessageConsumer implements RocketMQReplyListener<String, String> {

    @Override
    public String onMessage(String s) {
        System.out.printf("------- StringConsumerWithReplyString received: %s \n", s);
        return "reply string";
    }
}
