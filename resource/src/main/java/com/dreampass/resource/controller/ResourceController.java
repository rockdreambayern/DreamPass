package com.dreampass.resource.controller;

import com.dreampass.entity.Result;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.service.ResourceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/resource")
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    @PostMapping("/add")
    public Result<Void> addResource(@RequestBody ResourceDo resource) {
        resourceService.addResource(resource);
        return Result.success();
    }
}
