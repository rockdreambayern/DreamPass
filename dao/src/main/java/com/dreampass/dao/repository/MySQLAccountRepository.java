package com.dreampass.dao.repository;

import com.dreampass.dao.convert.AccountRoleRefConvert;
import com.dreampass.dao.mapper.AccountRoleRefPoMapper;
import com.dreampass.dao.model.AccountRoleRefPo;
import com.dreampass.dao.model.AccountRoleRefPoExample;
import com.dreampass.user.entity.AccountDo;
import com.dreampass.dao.convert.AccountConvert;
import com.dreampass.dao.mapper.AccountPoMapper;
import com.dreampass.dao.model.AccountPo;
import com.dreampass.dao.model.AccountPoExample;
import com.dreampass.infrastructure.exception.BizException;
import com.dreampass.user.entity.AccountRoleRefDo;
import com.dreampass.user.repository.AccountRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class MySQLAccountRepository implements AccountRepository {

    @Resource
    private AccountPoMapper accountMapper;

    @Resource
    private AccountRoleRefPoMapper accountRoleRefMapper;


    @Override
    public AccountDo loadAccount(String accountName) {
        AccountPoExample accountExample = new AccountPoExample();
        accountExample.createCriteria().andAccountNameEqualTo(accountName);
        List<AccountPo> accounts = accountMapper.selectByExample(accountExample);
        if (CollectionUtils.isEmpty(accounts)) {
            return null;
        }
        return AccountConvert.accountPo2Do(accounts.get(0));
    }

    @Override
    public void addAccount(AccountDo accountDo) {
        AccountPo po = AccountConvert.accountDo2Po(accountDo);
        try {
            if (accountMapper.insert(po) != 1) {
                throw new BizException("添加账号失败");
            }
        } catch (DuplicateKeyException e) {
            throw new BizException("账号已存在");
        }

    }

    @Override
    public void addAccountRoleRef(AccountRoleRefDo accountRoleRef) {
        Assert.notNull(accountRoleRef, "accountRoleRef is null");
        AccountRoleRefPo po = AccountRoleRefConvert.accountRoleRefDo2Po(accountRoleRef);
        if (accountRoleRefMapper.insert(po) != 1) {
            throw new BizException("添加账号角色关系失败");
        }
    }

    @Override
    public List<AccountRoleRefDo> listAccountRoleRefs(@Nonnull Long tenantId, @Nonnull String accountName) {
        AccountRoleRefPoExample example = new AccountRoleRefPoExample();
        example.createCriteria()
                        .andTenantIdEqualTo(tenantId)
                        .andAccountNameEqualTo(accountName)
                        .andDeleteTimeIsNull();
        List<AccountRoleRefPo> pos =  accountRoleRefMapper.selectByExample(example);
        return pos.stream().map(AccountRoleRefConvert::accountRoleRefPo2Do).toList();
    }
}
