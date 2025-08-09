package com.dreampass.auth.controller;

import com.dreampass.auth.controller.model.LoginRequest;
import com.dreampass.auth.controller.model.TokenResponse;
import com.dreampass.auth.service.AccountPermissionService;
import com.dreampass.auth.service.AuthService;
import com.dreampass.auth.util.JwtTokenUtils;
import com.dreampass.entity.Result;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.user.entity.AccountDo;
import com.dreampass.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private static final String BEARER= "Bearer ";

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private AuthService authService;

    @Resource
    private AccountPermissionService accountPermissionService;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getAccountName(), loginRequest.getPassword());
        return Result.success(new TokenResponse(token));
    }

    @GetMapping("/token/test")
    public Result<String> test(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace(BEARER, "");
        String accountName = JwtTokenUtils.getUsernameFromToken(token);
        return Result.success("当前用户：" + accountName);
    }

    @GetMapping("/resources")
    public Result<List<ResourceDo>> queryResources(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace(BEARER, "");
        String accountName = JwtTokenUtils.getUsernameFromToken(token);
        List<ResourceDo> resources = accountPermissionService.queryResources(accountName);
        return Result.success(resources);
    }

    @GetMapping("/account")
    public Result<AccountDo> getUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace(BEARER, "");
        String accountName = JwtTokenUtils.getUsernameFromToken(token);
        if (accountName == null) {
            return Result.success();
        }
        AccountDo account = userService.getAccount(accountName);
        return Result.success(account);
    }
}
