package com.dreampass.dao.repository;

import com.dreampass.dao.convert.RoleConvert;
import com.dreampass.dao.convert.RoleResourceRefConvert;
import com.dreampass.dao.mapper.RolePoMapper;
import com.dreampass.dao.mapper.RoleResourceRefPoMapper;
import com.dreampass.dao.model.RolePo;
import com.dreampass.dao.model.RoleResourceRefPo;
import com.dreampass.dao.model.RoleResourceRefPoExample;
import com.dreampass.role.entity.RoleDo;
import com.dreampass.role.entity.RoleResourceRefDo;
import com.dreampass.role.repository.RoleRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySQLRoleRepository implements RoleRepository {

    @Resource
    private RolePoMapper roleMapper;

    @Resource
    private RoleResourceRefPoMapper roleResourceRefMapper;

    @Override
    public void addRole(@Nonnull RoleDo role) {
        RolePo po = RoleConvert.roleDo2Po(role);
        roleMapper.insert(po);
    }

    @Override
    public void addResourceToRole(@Nonnull RoleResourceRefDo roleResourceRef) {
        RoleResourceRefPo po = RoleResourceRefConvert.roleResourceRefDo2Po(roleResourceRef);
        roleResourceRefMapper.insert(po);
    }

    @Override
    public List<RoleResourceRefDo> listRoleResourceRefs(@Nonnull Long tenantId, List<String> roleCodes) {
        RoleResourceRefPoExample example = new RoleResourceRefPoExample();
        example.createCriteria()
                .andTenantIdEqualTo(tenantId)
                .andRoleCodeIn(roleCodes)
                .andDeleteTimeIsNull();

        List<RoleResourceRefPo> pos = roleResourceRefMapper.selectByExample(example);
        return pos.stream().map(RoleResourceRefConvert::roleResourceRefPo2Do).toList();
    }
}
