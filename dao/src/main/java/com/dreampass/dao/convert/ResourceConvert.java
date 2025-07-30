package com.dreampass.dao.convert;
import java.time.ZoneId;
import java.util.Date;

import com.dreampass.dao.model.ResourcePo;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.enums.ResourceMethodEnum;
import com.dreampass.resource.enums.ResourceTypeEnum;

public class ResourceConvert {

    public static ResourcePo resourceDo2Po(ResourceDo resourceDo) {
        if (resourceDo == null) {
            return null;
        }
        ResourcePo po = new ResourcePo();
        po.setTenantId(resourceDo.getTenantId());
        po.setName(resourceDo.getName());
        po.setType(resourceDo.getType().getCode());
        po.setPath(resourceDo.getPath());
        po.setMethod(resourceDo.getMethod().getCode());
        po.setParentId(resourceDo.getParentId());
        po.setDescription(resourceDo.getDescription());
        po.setVisible(resourceDo.getVisible());
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return po;
    }

    public static ResourceDo resourcePo2Do(ResourcePo po) {
        if (po == null) {
            return null;
        }
        return ResourceDo.builder()
                .tenantId(po.getTenantId())
                .name(po.getName())
                .type(ResourceTypeEnum.findByCode(po.getType()))
                .path(po.getPath())
                .method(ResourceMethodEnum.findByCode(po.getType()))
                .parentId(po.getParentId())
                .description(po.getDescription())
                .visible(po.getVisible())
                .createTime(po.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .updateTime(po.getUpdateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
    }
}
