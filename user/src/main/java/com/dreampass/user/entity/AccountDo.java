package com.dreampass.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class AccountDo {

    private Long tenantId;

    private String accountName;

    private String userName;

    private String password;

    private String phoneNo;

    private Date updateTime;

    private Date createTime;
}
