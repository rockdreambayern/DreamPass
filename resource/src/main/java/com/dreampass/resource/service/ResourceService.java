package com.dreampass.resource.service;

import com.dreampass.infrastructure.tenant.annotation.Tenantable;
import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.repository.ResourceRepository;
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
        return resourceRepository.loads(tenantId, resourceNames);
    }
}
