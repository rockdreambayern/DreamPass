package com.dreampass.user.repository;

import com.dreampass.user.entity.AccountDo;
import com.dreampass.user.entity.AccountRoleRefDo;

import java.util.List;

public interface AccountRepository {

    /**
     * 加载账号
     * @param accountName 账号名
     * @return Account
     */
    AccountDo loadAccount(String accountName);

    /**
     * 添加账号
     * @param account 账号
     */
    void addAccount(AccountDo account);

    /**
     * 添加账号角色关系
     * @param accountRoleRef 账号角色关系
     */
    void addAccountRoleRef(AccountRoleRefDo accountRoleRef);

    /**
     * 根据账号名称查询账号角色关系列表
     * @param tenantId 租户ID
     * @param accountName 账号名称
     * @return List<AccountRoleRefDo> 账号角色关系列表
     */
    List<AccountRoleRefDo> listAccountRoleRefs(Long tenantId, String accountName);
}
