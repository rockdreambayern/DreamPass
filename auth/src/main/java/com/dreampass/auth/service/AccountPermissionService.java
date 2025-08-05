package com.dreampass.auth.service;

import com.dreampass.resource.entity.ResourceDo;
import com.dreampass.resource.service.ResourceService;
import com.dreampass.role.entity.RoleResourceRefDo;
import com.dreampass.role.service.RoleService;
import com.dreampass.user.entity.AccountRoleRefDo;
import com.dreampass.user.service.UserService;
import com.dreampass.util.ContextUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class AccountPermissionService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private ResourceService resourceService;

    public List<ResourceDo> queryResources(String accountName) {
        Long tenantId = ContextUtils.getTenantId();
        List<AccountRoleRefDo> accountRoleRefs = userService.listAccountRoleRefs(tenantId, accountName);
        if (CollectionUtils.isEmpty(accountRoleRefs)) {
            return Collections.emptyList();
        }
        List<String> roleCodes = accountRoleRefs.stream().map(AccountRoleRefDo::getRoleCode).toList();
        List<RoleResourceRefDo> roleResourceRefs = roleService.listRoleResourceRefs(tenantId, roleCodes);
        List<String> resourceNames = roleResourceRefs.stream().map(RoleResourceRefDo::getResourceName).toList();
        return resourceService.listResources(tenantId, resourceNames);
    }
}
