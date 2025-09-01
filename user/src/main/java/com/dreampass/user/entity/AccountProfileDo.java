package com.dreampass.user.entity;

import com.dreampass.infrastructure.tenant.TenantBindable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 账号档案
 */
@Getter
@Builder
public class AccountProfileDo implements TenantBindable  {

    @Setter
    private Long tenantId;

    private String accountName;

    private String avatarKey;

    private String password;

    private String phoneNo;

    private Date updateTime;

    private Date createTime;
}
