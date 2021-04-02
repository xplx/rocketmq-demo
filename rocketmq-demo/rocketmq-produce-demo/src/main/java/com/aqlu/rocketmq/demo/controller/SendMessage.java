package com.aqlu.rocketmq.demo.controller;

import com.aqlu.rocketmq.demo.domain.OrderPaidEvent;
import com.aqlu.rocketmq.demo.domain.User;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/send")
@RestController
public class SendMessage {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/msg")
    public String SendMessage() {
        rocketMQTemplate.sendOneWay("string-topic:message", "Hello, World!");
        return "hello world";
    }

    @RequestMapping("/objectMsg")
    public String SendObject() {
        OrderPaidEvent orderPaidEvent = new OrderPaidEvent("1", new BigDecimal("88.00"));
        rocketMQTemplate.sendOneWay("bingo-purchaseOrder-com-topic:OrderDelivery", orderPaidEvent);
        return "success";
    }

    @RequestMapping("/tag0")
    public String SendTag0() {
        rocketMQTemplate.convertAndSend("message-ext-topic:tag0", "I'm from tag0");
        return "success";
    }

    @RequestMapping("/tag1")
    public String SendTag1() {
        rocketMQTemplate.convertAndSend("message-ext-topic:tag1", "I'm from tag1");
        return "success";
    }

    /**
     * 发送同步消息，同步发送需要在方法的参数中指明返回值类型
     *
     * @return
     */
    @RequestMapping("/syn")
    public String SendSyn() {
        String replyString = rocketMQTemplate.sendAndReceive("stringRequestTopic:tag1", "request string", String.class);
        System.out.printf("send %s and receive %s %n", "request string", replyString);
        return "success";
    }

    /**
     * 发送异步消息，异步发送需要在回调的接口中指明返回值类型
     *
     * @return
     */
    @RequestMapping("/asyn")
    public String SendAsyn() {
        rocketMQTemplate.sendAndReceive("objectRequestTopic", new User().setUserAge((byte) 18).setUserName("Kitty"), new RocketMQLocalRequestCallback<User>() {
            @Override
            public void onSuccess(User message) {
                System.out.printf("send user object and receive %s %n", message.toString());
            }
            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }
        }, 5000);
        return "success";
    }

    /**
     * 发送异步消息，异步发送需要在回调的接口中指明返回值类型
     *
     * @return
     */
    @RequestMapping("/sequence")
    public String SendSequence() {
        rocketMQTemplate.syncSendOrderly("orderly_topic",
                MessageBuilder.withPayload("Hello, World").build(), "hashkey");
        return null;
    }
}
