package com.dreampass.util;

public class ContextUtils {

    private static final ThreadLocal<Long> tenantThreadLocal = new ThreadLocal<>();

    public static Long getTenantId() {
        return tenantThreadLocal.get();
    }

    public static void saveTenantId(Long tenantId) {
        tenantThreadLocal.set(tenantId);
    }
}
