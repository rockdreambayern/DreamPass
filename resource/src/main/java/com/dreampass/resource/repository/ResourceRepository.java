package com.dreampass.resource.repository;

import com.dreampass.resource.entity.ResourceDo;

import java.util.List;

/**
 *
 */
public interface ResourceRepository {

    void addResource(ResourceDo resource);

    List<ResourceDo> loads(List<String> resourceCodes);
}
