package com.dreampass.test.repository.condition;

import com.dreampass.test.enums.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderFilterCondition {

    private List<OrderStatusEnum> statusList;

}
