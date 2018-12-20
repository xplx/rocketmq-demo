package com.aqlu.rocketmq.demo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OrderPaidEvent Created by aqlu on 2017/11/16.
 */
@Data
public class OrderPaidEvent implements Serializable {

    private String orderId;

    private BigDecimal paidMoney;

    public OrderPaidEvent(String orderId, BigDecimal paidMoney) {
        this.orderId = orderId;
        this.paidMoney = paidMoney;
    }
}
