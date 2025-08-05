package com.dreampass.user.controller;

import com.dreampass.entity.AccountDo;
import com.dreampass.user.entity.AccountRoleRefDo;
import com.dreampass.user.entity.UserDo;
import com.dreampass.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/v1/user/add")
    public ResponseEntity<Void> addUser(@RequestBody UserDo user) {
        AccountDo accountDo = AccountDo
                .builder()
                .accountName(user.getAccountName())
                .userName(user.getName())
                .password(user.getPassword())
                .phoneNo(user.getPhoneNo())
                .build();
        userService.register(accountDo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/user/add_role")
    public ResponseEntity<Void> addRoleToAccount(@RequestBody AccountRoleRefDo accountRoleRef) {
        userService.addRoleToAccount(accountRoleRef);
        return ResponseEntity.ok().build();
    }
}
