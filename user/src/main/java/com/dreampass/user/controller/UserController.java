package com.dreampass.user.controller;

import com.dreampass.entity.AccountDo;
import com.dreampass.user.model.UserDo;
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
    public void addUser(@RequestBody UserDo user) {
        log.debug("user:{}", user);
        AccountDo accountDo = AccountDo.builder()
                        .accountName(user.getAccountName())
                                .userName(user.getName())
                                        .password(user.getPassword())
                                                .phoneNo(user.getPhoneNo())
                                                        .build();
        userService.register(accountDo);
    }
}
