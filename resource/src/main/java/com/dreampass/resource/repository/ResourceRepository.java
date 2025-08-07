package com.dreampass.resource.repository;

import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.enums.ResourceTypeEnum;

import java.util.List;

/**
 *
 */
public interface ResourceRepository {

    /**
     * 新增资源
     * @param resource 资源
     */
    void addResource(ResourceDo resource);

    /**
     * 加载资源列表
     * @param tenantId   租户ID
     * @param resourceNames  资源名称
     * @return List<ResourceDo> 资源列表
     */
    List<ResourceDo> load(Long tenantId, List<String> resourceNames);

    /**
     * 加载资源列表
     * @param tenantId  租户ID
     * @param resourceType 资源类型，参考 {@link com.dreampass.resource.enums.ResourceTypeEnum}
     * @param path 路径
     * @return List<ResourceDo> 资源列表
     */
    List<ResourceDo> load(Long tenantId, ResourceTypeEnum resourceType, String path);
}
