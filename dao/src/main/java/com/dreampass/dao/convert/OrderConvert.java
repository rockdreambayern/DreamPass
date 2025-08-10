package com.dreampass.dao.convert;

import com.dreampass.dao.model.OrderPo;
import com.dreampass.test.entity.OrderDo;
import com.dreampass.test.enums.OrderStatusEnum;

import java.time.ZoneId;

public class OrderConvert {

    private OrderConvert() {

    }

    public static OrderDo orderPo2Do(OrderPo po) {
        return OrderDo.builder()
                .name(po.getName())
                .description(po.getDescription())
                .imgUrl(po.getImgUrl())
                .price(po.getPrice())
                .orderStatus(OrderStatusEnum.valueOf(po.getOrderStatus()))
                .creator(po.getCreator())
                .updater(po.getUpdater())
                .createTime(po.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .updateTime(po.getUpdateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
    }
}
