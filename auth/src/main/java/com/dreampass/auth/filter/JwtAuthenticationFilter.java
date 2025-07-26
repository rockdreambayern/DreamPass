package com.dreampass.auth.filter;

import com.dreampass.auth.util.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // 从 token 中解析用户名
                String username = JwtTokenUtils.getUsernameFromToken(token);

                // 如果当前上下文没有用户，且 token 有效
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 获取权限信息
                    List<SimpleGrantedAuthority> authorities = JwtTokenUtils.getAuthoritiesFromToken(token);

                    // 构建认证对象
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    // 设置用户到 Spring Security 上下文中
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                // token 解析失败，可能已过期或非法，记录或抛出异常都可以
                logger.warn("JWT token 解析失败: " + e.getMessage());
                throw new ServletException("Token无效，请重新登录");
            }
        }

        // 放行
        filterChain.doFilter(request, response);
    }
}
