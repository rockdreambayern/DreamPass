package com.dreampass.user.controller;

import com.dreampass.entity.Result;
import com.dreampass.user.entity.AccountDo;
import com.dreampass.user.entity.AccountRoleRefDo;
import com.dreampass.user.entity.UserDo;
import com.dreampass.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/v1/user/add")
    public Result<Void> addUser(@RequestBody UserDo user) {
        AccountDo accountDo = AccountDo
                .builder()
                .accountName(user.getAccountName())
                .userName(user.getName())
                .password(user.getPassword())
                .phoneNo(user.getPhoneNo())
                .build();
        userService.register(accountDo);
        return Result.success();
    }

    @PostMapping("/v1/user/add_role")
    public Result<Void> addRoleToAccount(@RequestBody AccountRoleRefDo accountRoleRef) {
        userService.addRoleToAccount(accountRoleRef);
        return Result.success();
    }
}
