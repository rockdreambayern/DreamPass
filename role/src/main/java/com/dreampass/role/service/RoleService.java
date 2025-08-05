package com.dreampass.role.service;

import com.dreampass.infrastructure.tenant.annotation.Tenantable;
import com.dreampass.role.entity.RoleDo;
import com.dreampass.role.entity.RoleResourceRefDo;
import com.dreampass.role.repository.RoleRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Tenantable
    public void addRole(RoleDo role) {
        roleRepository.addRole(role);
    }

    @Tenantable
    public void addResourceToRole(RoleResourceRefDo roleResourceRef) {
        roleRepository.addResourceToRole(roleResourceRef);
    }

    /**
     * 查询角色资源关系列表
     * @param tenantId 租户ID
     * @param roleCodes 角色码列表
     * @return List<RoleResourceRefDo>
     */
    public List<RoleResourceRefDo> listRoleResourceRefs(Long tenantId, List<String> roleCodes) {
        return roleRepository.listRoleResourceRefs(tenantId, roleCodes);
    }
}
