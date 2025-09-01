package com.dreampass.test.controller;

import com.dreampass.entity.Result;
import com.dreampass.infrastructure.tenant.annotation.Tenantable;
import com.dreampass.test.controller.model.ListOrderRequest;
import com.dreampass.test.entity.OrderDo;
import com.dreampass.test.enums.OrderStatusEnum;
import com.dreampass.test.repository.OrderRepository;
import com.dreampass.test.repository.condition.OrderFilterCondition;
import jakarta.annotation.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        String numberStr = fileName.replaceAll("\\D+", ""); // 去掉非数字
        if (numberStr.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        int number = Integer.parseInt(numberStr);

        // 获取 resource/images 下所有 jpg 文件
        org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/images/*.jpg");
        if (resources.length == 0) {
            return ResponseEntity.notFound().build();
        }

        // 取模
        int index = number % resources.length;
        org.springframework.core.io.Resource selectedImage = resources[index];

        // 读取文件内容
        byte[] imageBytes = selectedImage.getInputStream().readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
 }
