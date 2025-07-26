package com.dreampass.auth.controller;

import com.dreampass.auth.controller.model.LoginRequest;
import com.dreampass.auth.controller.model.TokenResponse;
import com.dreampass.auth.util.JwtTokenUtils;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        // 1. 封装用户名密码为认证对象
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        // 2. 认证用户（会自动调用 UserDetailsService.loadUserByUsername）
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 3. 获取认证后的 UserDetails（包含权限信息）
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 4. 生成 Token
        String token = JwtTokenUtils.generateToken(userDetails);

        // 5. 返回给前端
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
