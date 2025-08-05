package com.dreampass.dao.convert;
import java.time.ZoneId;
import java.util.Date;

import com.dreampass.dao.model.RolePo;
import com.dreampass.role.entity.RoleDo;

public class RoleConvert {

    private RoleConvert() {

    }

    public static RolePo roleDo2Po(RoleDo roleDo) {
        if (roleDo == null) {
            return null;
        }
        RolePo po = new RolePo();
        po.setTenantId(roleDo.getTenantId());
        po.setCode(roleDo.getCode());
        po.setName(roleDo.getName());
        po.setDescription(roleDo.getDescription());
        po.setBuiltIn(roleDo.getBuiltIn());
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return po;
    }

    public static RoleDo rolePo2Do(RolePo po) {
        if (po == null) {
            return null;
        }
        return RoleDo.builder()
                .tenantId(po.getTenantId())
                .code(po.getCode())
                .name(po.getName())
                .description(po.getName())
                .builtIn(po.getBuiltIn())
                .createTime(po.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .updateTime(po.getUpdateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
    }
}
