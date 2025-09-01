package com.dreampass.auth.filter;

import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.enums.ResourceTypeEnum;
import com.dreampass.resource.service.ResourceService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class ResourcePermissionFilter extends OncePerRequestFilter {

    @Resource
    private ResourceService resourceService;

    private static final List<String> NOT_AUTH_URIS = List.of("/v1/user/add", "/v1/auth/login");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 排除无需处理的路径
        if (NOT_AUTH_URIS.contains(request.getRequestURI()) || request.getRequestURI().startsWith("/s3")) {
            filterChain.doFilter(request, response);
            return;
        }

        ResourceDo resource = resourceService.getAPIResource(request.getRequestURI());
        // 为了节省资源配置的时间，API未配置资源无需进行权限控制。
        if (resource == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof List) {
            @SuppressWarnings("unchecked")
            List<ResourceDo> resources = (List<ResourceDo>) authentication.getDetails();

            String path = request.getRequestURI();
            String method = request.getMethod();

            boolean allowed = resources.stream()
                    .filter(res -> res.getType() == ResourceTypeEnum.API)
                    .anyMatch(res -> Objects.equals(res.getPath(), path)
                            && res.getMethod().name().equalsIgnoreCase(method));

            if (!allowed) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权限访问该接口");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
