package com.dreampass.test.controller;

import com.dreampass.entity.Result;
import com.dreampass.infrastructure.tenant.annotation.Tenantable;
import com.dreampass.test.controller.model.ListOrderRequest;
import com.dreampass.test.entity.OrderDo;
import com.dreampass.test.enums.OrderStatusEnum;
import com.dreampass.test.repository.OrderRepository;
import com.dreampass.test.repository.condition.OrderFilterCondition;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {

    @Resource
    private OrderRepository orderRepository;

    @PostMapping("/v1/order")
    @Tenantable
    public Result<List<OrderDo>> listOrders(@RequestBody ListOrderRequest request) {
        List<OrderStatusEnum> statusEnums = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getStatusList())) {
            statusEnums = request.getStatusList().stream().map(OrderStatusEnum::findByCode).filter(Objects::nonNull).toList();
        }
        OrderFilterCondition condition = OrderFilterCondition.builder().statusList(statusEnums).build();

        List<OrderDo> orders = orderRepository.listOrders(condition, request.getSize(), request.getPage());
        return Result.success(orders);
    }
}
