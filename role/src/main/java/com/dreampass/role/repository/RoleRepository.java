package com.dreampass.role.repository;

import com.dreampass.role.entity.RoleDo;
import com.dreampass.role.entity.RoleResourceRefDo;

import java.util.List;

public interface RoleRepository {

    void addRole(RoleDo role);

    void addResourceToRole(RoleResourceRefDo roleResourceRef);

    List<RoleResourceRefDo> listRoleResourceRefs(Long tenantId, List<String> roleCodes);
}
