package com.dreampass.role.entity;

import com.dreampass.infrastructure.tenant.TenantBindable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RoleResourceRefDo implements TenantBindable {

    private Long id;

    @Setter
    private Long tenantId;

    private String roleCode;

    private String resourceName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
