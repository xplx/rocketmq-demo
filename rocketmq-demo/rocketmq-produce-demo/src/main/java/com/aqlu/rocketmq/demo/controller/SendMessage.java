package com.aqlu.rocketmq.demo.controller;

import com.aqlu.rocketmq.demo.domain.OrderPaidEvent;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@RequestMapping("/send")
@Controller
public class SendMessage {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @RequestMapping("/msg")
    public String SendMessage(){
        rocketMQTemplate.sendOneWay("string-topic", "Hello, World!");
        return "hello world";
    }

    @RequestMapping("/objectMsg")
    public String SendObject(){
        OrderPaidEvent orderPaidEvent = new OrderPaidEvent("1",new BigDecimal("88.00"));
        rocketMQTemplate.sendOneWay("order-paid-topic", orderPaidEvent);
        return "success";
    }
    @RequestMapping("/tag0")
    public String SendTag0(){
        rocketMQTemplate.convertAndSend("message-ext-topic:tag0", "I'm from tag0");
        return "success";
    }
    @RequestMapping("/tag1")
    public String SendTag1(){
        rocketMQTemplate.convertAndSend("message-ext-topic:tag1", "I'm from tag1");
        return "success";
    }
}
