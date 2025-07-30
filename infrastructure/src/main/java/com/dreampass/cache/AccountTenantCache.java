package com.dreampass.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class AccountTenantCache {

    private Cache<String, Long> tenantCache;

    @PostConstruct
    public void init() {
        tenantCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();
    }

    public Long getTenantId(String accountName, Function<String, Long> loader) {
        return tenantCache.get(accountName, loader);
    }

    public void invalidate(String accountName) {
        tenantCache.invalidate(accountName);
    }

    public void clearAll() {
        tenantCache.invalidateAll();
    }
}
