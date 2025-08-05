package com.dreampass.role.entity;

import com.dreampass.infrastructure.tenant.TenantBindable;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RoleDo implements TenantBindable {

    private Long id;

    private Long tenantId;

    private String code;

    private String name;        // 角色名称（显示用）

    private String description; // 描述

    private Boolean builtIn;    // 是否是系统内置角色，不能删除

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Override
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
