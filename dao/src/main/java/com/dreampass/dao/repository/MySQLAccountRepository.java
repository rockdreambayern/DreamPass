package com.dreampass.dao.repository;

import com.dreampass.entity.AccountDo;
import com.dreampass.repository.AccountRepository;
import com.dreampass.dao.convert.AccountConvert;
import com.dreampass.dao.mapper.AccountPoMapper;
import com.dreampass.dao.model.AccountPo;
import com.dreampass.dao.model.AccountPoExample;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class MySQLAccountRepository implements AccountRepository {

    @Resource
    private AccountPoMapper accountMapper;


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
        if (accountMapper.insert(po) != 1) {
            throw new RuntimeException("添加账号失败");
        }
    }
}
