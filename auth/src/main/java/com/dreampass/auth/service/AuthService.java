package com.dreampass.auth.service;

import com.dreampass.auth.util.JwtTokenUtils;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    public String login(String accountName, String password) {
        // 1. 封装用户名密码为认证对象
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(accountName, password);

        // 2. 认证用户（会自动调用 UserDetailsService.loadUserByUsername）
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 3. 获取认证后的 UserDetails（包含权限信息）
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 4. 生成 Token
        return JwtTokenUtils.generateToken(userDetails);
    }
}
