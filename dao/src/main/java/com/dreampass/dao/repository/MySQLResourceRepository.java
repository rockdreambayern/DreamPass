package com.dreampass.dao.repository;

import com.dreampass.dao.convert.ResourceConvert;
import com.dreampass.dao.mapper.ResourcePoMapper;
import com.dreampass.dao.model.ResourcePo;
import com.dreampass.dao.model.ResourcePoExample;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.enums.ResourceTypeEnum;
import com.dreampass.resource.repository.ResourceRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Repository
public class MySQLResourceRepository implements ResourceRepository {

    @Resource
    private ResourcePoMapper resourceMapper;

    @Override
    public void addResource(@Nonnull ResourceDo resource) {
        ResourcePo po = ResourceConvert.resourceDo2Po(resource);
        resourceMapper.insert(po);
    }

    @Override
    public List<ResourceDo> load(Long tenantId, List<String> resourceNames) {
        if (CollectionUtils.isEmpty(resourceNames)) {
            return Collections.emptyList();
        }
        ResourcePoExample example = new ResourcePoExample();
        example.createCriteria().andTenantIdEqualTo(tenantId).andNameIn(resourceNames).andDeleteTimeIsNull();
        List<ResourcePo> pos = resourceMapper.selectByExample(example);
        return pos.stream().map(ResourceConvert::resourcePo2Do).toList();
    }

    @Override
    public List<ResourceDo> load(@Nonnull Long tenantId, @Nonnull ResourceTypeEnum resourceType, @Nonnull String path) {
        ResourcePoExample example = new ResourcePoExample();
        example.createCriteria().andTenantIdEqualTo(tenantId)
                .andTypeEqualTo(resourceType.getCode()).andPathEqualTo(path).andDeleteTimeIsNull();
        List<ResourcePo> pos = resourceMapper.selectByExample(example);
        return pos.stream().map(ResourceConvert::resourcePo2Do).toList();
    }
}
