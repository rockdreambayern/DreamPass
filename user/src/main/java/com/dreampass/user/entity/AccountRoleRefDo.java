package com.dreampass.user.entity;

import com.dreampass.infrastructure.tenant.TenantBindable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 账号角色关系
 */
@Getter
@Builder
public class AccountRoleRefDo implements TenantBindable {

    @Setter
    private Long tenantId;

    private String accountName;

    private String roleCode;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
