package com.dreampass.auth.controller;

import com.dreampass.auth.controller.model.LoginRequest;
import com.dreampass.auth.controller.model.TokenResponse;
import com.dreampass.auth.service.AccountPermissionService;
import com.dreampass.auth.service.AuthService;
import com.dreampass.auth.util.JwtTokenUtils;
import com.dreampass.resource.entity.ResourceDo;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private AuthService authService;

    @Resource
    private AccountPermissionService accountPermissionService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getAccountName(), loginRequest.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/token/test")
    public ResponseEntity<String> test(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String accountName = JwtTokenUtils.getUsernameFromToken(token);
        return ResponseEntity.ok("当前用户：" + accountName);
    }

    @GetMapping("/resources")
    public ResponseEntity<List<ResourceDo>> queryResources(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String accountName = JwtTokenUtils.getUsernameFromToken(token);
        List<ResourceDo> resources = accountPermissionService.queryResources(accountName);
        return ResponseEntity.ok(resources);
    }
}
