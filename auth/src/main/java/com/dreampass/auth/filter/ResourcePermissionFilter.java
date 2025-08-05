package com.dreampass.auth.filter;

import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.enums.ResourceTypeEnum;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

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
