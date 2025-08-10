package com.dreampass.test.repository;

import com.dreampass.test.entity.OrderDo;
import com.dreampass.test.repository.condition.OrderFilterCondition;

import java.util.List;

public interface OrderRepository {

    List<OrderDo> listOrders(OrderFilterCondition orderFilterCondition, int size, int page);
}
