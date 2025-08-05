package com.dreampass.auth.filter;

import com.dreampass.auth.service.AccountPermissionService;
import com.dreampass.auth.util.JwtTokenUtils;
import com.dreampass.cache.AccountTenantCache;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.user.repository.AccountRepository;
import com.dreampass.util.ContextUtils;
import io.jsonwebtoken.lang.Assert;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private AccountTenantCache accountTenantCache;

    @Resource
    private AccountPermissionService accountPermissionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT Filter executing for URI: {}", request.getRequestURI());

        // 排除无需处理的路径
        if (request.getRequestURI().endsWith("/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // 从 token 中解析用户名
                String accountName = JwtTokenUtils.getUsernameFromToken(token);

                if (accountName != null) {
                    // 解析并缓存租户信息
                    resolveAndStoreTenantId(accountName);
                }

                // 如果当前上下文没有用户，且 token 有效
                if (accountName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 获取权限信息
                    List<SimpleGrantedAuthority> authorities = JwtTokenUtils.getAuthoritiesFromToken(token);

                    // 构建认证对象
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(accountName, null, authorities);

                    List<ResourceDo> resources = accountPermissionService.queryResources(accountName);
                    auth.setDetails(resources);

                    // 设置用户到 Spring Security 上下文中
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    log.info("Authentication set: {}", auth);
                }
            } catch (Exception e) {
                // token 解析失败，可能已过期或非法，记录或抛出异常都可以
                logger.warn("JWT token 解析失败: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token无效，请重新登录");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token无效，请重新登录");
            return;
        }

        // 放行
        filterChain.doFilter(request, response);
    }

    private void resolveAndStoreTenantId(String accountName) {
        Long tenantId = accountTenantCache.getTenantId(accountName, key -> accountRepository.loadAccount(accountName).getTenantId());
        Assert.notNull(tenantId, "无法获取租户信息");
        ContextUtils.saveTenantId(tenantId);
    }
}
