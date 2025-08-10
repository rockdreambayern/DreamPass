package com.dreampass.dao.repository;

import com.dreampass.dao.convert.OrderConvert;
import com.dreampass.dao.mapper.OrderPoMapper;
import com.dreampass.dao.model.OrderPo;
import com.dreampass.dao.model.OrderPoExample;
import com.dreampass.test.entity.OrderDo;
import com.dreampass.test.repository.OrderRepository;
import com.dreampass.test.repository.condition.OrderFilterCondition;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySQLOrderRepository implements OrderRepository {

    @Resource
    private OrderPoMapper orderMapper;

    @Override
    public List<OrderDo> listOrders(OrderFilterCondition orderFilterCondition, int size, int page) {
        OrderPoExample example = new OrderPoExample();

        // 排序规则
        example.setOrderByClause("create_time DESC");

        // 构造查询条件
        OrderPoExample.Criteria criteria = example.createCriteria();

        // 如果有状态条件
        if (orderFilterCondition != null && orderFilterCondition.getStatusList() != null
                && !orderFilterCondition.getStatusList().isEmpty()) {
            // 假设枚举 OrderStatusEnum.toString() 返回数据库存的值
            List<String> statusStrList = orderFilterCondition.getStatusList()
                    .stream()
                    .map(Enum::name) // 或者用 .toString()，看你的存储规则
                    .toList();
            criteria.andOrderStatusIn(statusStrList);
        }

        // 分页（offset, limit）
        int offset = (page - 1) * size;
        example.setOffset(offset);
        example.setLimit(size);

        List<OrderPo> pos = orderMapper.selectByExample(example);

        return pos.stream().map(OrderConvert::orderPo2Do).toList();
    }
}
