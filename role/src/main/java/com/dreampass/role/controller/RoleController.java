package com.dreampass.role.controller;

import com.dreampass.entity.Result;
import com.dreampass.role.entity.RoleDo;
import com.dreampass.role.entity.RoleResourceRefDo;
import com.dreampass.role.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    public Result<Void> addRole(@RequestBody RoleDo role) {
        roleService.addRole(role);
        return Result.success();
    }

    @PostMapping("/add_resource")
    public Result<Void> addResourceToRole(@RequestBody RoleResourceRefDo roleResourceRef) {
        roleService.addResourceToRole(roleResourceRef);
        return Result.success();
    }
}
