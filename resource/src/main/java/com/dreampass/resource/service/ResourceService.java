package com.dreampass.resource.service;

import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.repository.ResourceRepository;
import com.dreampass.util.ContextUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    public void addResource(ResourceDo resource) {
        resource.setTenantId(ContextUtils.getTenantId());
        resourceRepository.addResource(resource);
    }
}
