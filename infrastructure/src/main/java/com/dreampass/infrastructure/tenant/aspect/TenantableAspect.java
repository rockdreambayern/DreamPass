package com.dreampass.infrastructure.tenant.aspect;

import com.dreampass.infrastructure.tenant.TenantBindable;
import com.dreampass.util.ContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantableAspect {

    @Before("@annotation(com.dreampass.infrastructure.tenant.annotation.Tenantable)")
    public void before(JoinPoint joinPoint) {
        Long tenantId = ContextUtils.getTenantId();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof TenantBindable) {
                ((TenantBindable) arg).setTenantId(tenantId);
            }
        }
    }
}
