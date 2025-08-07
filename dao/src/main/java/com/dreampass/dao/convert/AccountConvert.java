package com.dreampass.dao.convert;

import com.dreampass.user.entity.AccountDo;
import com.dreampass.dao.model.AccountPo;

import java.util.Date;

public class AccountConvert {

    private AccountConvert() {
    }

    public static AccountDo accountPo2Do(AccountPo po) {
        return AccountDo.builder()
                .tenantId(po.getTenantId())
                .accountName(po.getAccountName())
                .userName(po.getUserName())
                .password(po.getPassword())
                .password(po.getPassword())
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
