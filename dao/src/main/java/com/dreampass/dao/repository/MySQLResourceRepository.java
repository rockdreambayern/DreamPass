package com.dreampass.dao.repository;

import com.dreampass.dao.convert.ResourceConvert;
import com.dreampass.dao.mapper.ResourcePoMapper;
import com.dreampass.dao.model.ResourcePo;
import com.dreampass.dao.model.ResourcePoExample;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.repository.ResourceRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Repository
public class MySQLResourceRepository implements ResourceRepository {

    @Resource
    private ResourcePoMapper resourceMapper;

    @Override
    public void addResource(ResourceDo resource) {
        Assert.notNull(resource, "resource is null");
        ResourcePo po = ResourceConvert.resourceDo2Po(resource);
        resourceMapper.insert(po);
    }

    @Override
    public List<ResourceDo> loads(List<String> resourceCodes) {
        if (CollectionUtils.isEmpty(resourceCodes)) {
            return Collections.emptyList();
        }
        ResourcePoExample example = new ResourcePoExample();
        example.createCriteria().andNameIn(resourceCodes);
        List<ResourcePo> pos = resourceMapper.selectByExample(example);
        return pos.stream().map(ResourceConvert::resourcePo2Do).toList();
    }
}
