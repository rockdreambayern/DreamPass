package com.dreampass.dao.convert;
import java.time.ZoneId;
import java.util.Date;

import com.dreampass.dao.model.AccountRoleRefPo;
import com.dreampass.user.entity.AccountRoleRefDo;

public class AccountRoleRefConvert {

    public static AccountRoleRefDo accountRoleRefPo2Do(AccountRoleRefPo po) {
        return AccountRoleRefDo.builder()
                .accountName(po.getAccountName())
                .roleCode(po.getRoleCode())
                .createTime(po.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .updateTime(po.getUpdateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
    }

    public static AccountRoleRefPo accountRoleRefDo2Po(AccountRoleRefDo accountRoleRefDo) {
        AccountRoleRefPo po = new AccountRoleRefPo();
        po.setTenantId(accountRoleRefDo.getTenantId());
        po.setAccountName(accountRoleRefDo.getAccountName());
        po.setRoleCode(accountRoleRefDo.getRoleCode());
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return po;
    }
}
