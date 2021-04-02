package com.aqlu.rocketmq.demo.consumer;

import com.aqlu.rocketmq.demo.domain.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Service;

/**
 * @author xiaopeng
 * @date 2021年03月29日 16:13
 * @description 消息异步
 * 需要实现RocketMQReplyListener<T, R> 接口，其中T表示接收值的类型，R表示返回值的类型。
 */
@Service
@RocketMQMessageListener(topic = "objectRequestTopic", consumerGroup = "AStringRequestConsumer")
public class ASynMessageConsumer implements RocketMQReplyListener<User, User>{


    @Override
    public User onMessage(User user) {
        System.out.printf("------- ObjectConsumerWithReplyUser received: %s \n", user);
        User replyUser = new User().setUserName("测试返回数据");
        return replyUser;
    }
}
