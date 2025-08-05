package com.dreampass.user.service;

import com.dreampass.entity.AccountDo;
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
        String encryptedPassword = PasswordUtils.hashPassword(account.getPassword());
        log.debug("加密后密码:{}", encryptedPassword);
        account.setPassword(encryptedPassword);
        accountRepository.addAccount(account);
    }

    @Tenantable
    public void addRoleToAccount(AccountRoleRefDo accountRoleRef) {
        accountRepository.addAccountRoleRef(accountRoleRef);
    }

    public List<AccountRoleRefDo> listAccountRoleRefs(Long tenantId, String accountName) {
        return accountRepository.listAccountRoleRefs(tenantId, accountName);
    }
}
