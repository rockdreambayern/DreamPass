package com.dreampass.repository;

import com.dreampass.entity.AccountDo;

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
}
