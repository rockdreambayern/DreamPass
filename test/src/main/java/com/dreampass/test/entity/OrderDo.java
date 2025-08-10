package com.dreampass.test.entity;

import com.dreampass.test.enums.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderDo {

    private String name;

    private String description;

    private String imgUrl;

    private BigDecimal price;

    private OrderStatusEnum orderStatus;

    private String creator;

    private String updater;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
