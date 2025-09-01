package com.dreampass.dao.convert;

import com.dreampass.dao.model.AccountProfilePo;
import com.dreampass.user.entity.AccountDo;
import com.dreampass.dao.model.AccountPo;
import com.dreampass.user.entity.AccountProfileDo;

import java.util.Date;
import java.util.Optional;

public class AccountConvert {

    private AccountConvert() {
    }

    public static AccountDo accountPo2Do(AccountPo po, AccountProfilePo profilePo) {
        return AccountDo.builder()
                .tenantId(po.getTenantId())
                .accountName(po.getAccountName())
                .userName(po.getUserName())
                .password(po.getPassword())
                .password(po.getPassword())
                .avatarKey(Optional.ofNullable(profilePo).map(AccountProfilePo::getAvatarKey).orElse(null))
                .updateTime(po.getUpdateTime())
                .createTime(po.getCreateTime())
                .build();
    }

    public static AccountPo accountDo2Po(AccountDo accountDo) {
        AccountPo po = new AccountPo();
        po.setTenantId(accountDo.getTenantId());
        po.setAccountName(accountDo.getAccountName());
        po.setUserName(accountDo.getUserName());
        po.setPassword(accountDo.getPassword());
        po.setPhoneNo(accountDo.getPhoneNo());
        Date date = new Date();
        po.setCreateTime(date);
        po.setUpdateTime(date);
        return po;
    }
}
