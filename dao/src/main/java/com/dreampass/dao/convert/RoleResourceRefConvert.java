package com.dreampass.dao.convert;
import java.time.ZoneId;
import java.util.Date;

import com.dreampass.dao.model.RoleResourceRefPo;
import com.dreampass.role.entity.RoleResourceRefDo;

public class RoleResourceRefConvert {

    public static RoleResourceRefPo roleResourceRefDo2Po(RoleResourceRefDo roleResourceRefDo) {
        RoleResourceRefPo po = new RoleResourceRefPo();
        po.setTenantId(roleResourceRefDo.getTenantId());
        po.setRoleCode(roleResourceRefDo.getRoleCode());
        po.setResourceName(roleResourceRefDo.getResourceName());
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return po;
    }

    public static RoleResourceRefDo roleResourceRefPo2Do(RoleResourceRefPo po) {
        return RoleResourceRefDo.builder()
                .id(po.getId())
                .roleCode(po.getRoleCode())
                .resourceName(po.getResourceName())
                .createTime(po.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .updateTime(po.getUpdateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
    }
}
