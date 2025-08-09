package com.dreampass.user.service;

import com.dreampass.infrastructure.exception.BizException;
import com.dreampass.user.entity.AccountDo;
import com.dreampass.infrastructure.tenant.annotation.Tenantable;
import com.dreampass.user.entity.AccountRoleRefDo;
import com.dreampass.user.repository.AccountRepository;
import com.dreampass.user.common.PasswordUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Resource
    private AccountRepository accountRepository;

    public void register(AccountDo account) {
        if (!PasswordUtils.isStrong(account.getPassword())) {
            throw new BizException("密码强度不符合要求，需包含大小写字母、数字、特殊字符，且长度不少于8位");
        }

        String encryptedPassword = PasswordUtils.hashPassword(account.getPassword());
        account.setPassword(encryptedPassword);
        account.setTenantId(123L);
        accountRepository.addAccount(account);
    }

    @Tenantable
    public void addRoleToAccount(AccountRoleRefDo accountRoleRef) {
        accountRepository.addAccountRoleRef(accountRoleRef);
    }

    public List<AccountRoleRefDo> listAccountRoleRefs(Long tenantId, String accountName) {
        return accountRepository.listAccountRoleRefs(tenantId, accountName);
    }

    @Tenantable
    public AccountDo getAccount(String accountName) {
        return accountRepository.loadAccount(accountName);
    }
}
