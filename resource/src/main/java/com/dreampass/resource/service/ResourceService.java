package com.dreampass.resource.service;

import com.dreampass.infrastructure.exception.SystemException;
import com.dreampass.infrastructure.tenant.annotation.Tenantable;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.enums.ResourceTypeEnum;
import com.dreampass.resource.repository.ResourceRepository;
import com.dreampass.util.ContextUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Tenantable
    public void addResource(ResourceDo resource) {
        resourceRepository.addResource(resource);
    }

    public List<ResourceDo> listResources(Long tenantId, List<String> resourceNames) {
        return resourceRepository.load(tenantId, resourceNames);
    }

    @Tenantable
    public ResourceDo getAPIResource(String path) {
        List<ResourceDo> resources = resourceRepository.load(ContextUtils.getTenantId(), ResourceTypeEnum.API, path);
        if (resources.size() > 1) {
            throw new SystemException(String.format("API resource %s is not unique", path));
        }
        return resources.isEmpty() ? null : resources.get(0);
    }
}
