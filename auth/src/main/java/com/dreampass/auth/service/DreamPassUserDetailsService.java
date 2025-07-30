package com.dreampass.auth.service;

import com.dreampass.entity.AccountDo;
import com.dreampass.repository.AccountRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

@Service
public class DreamPassUserDetailsService implements UserDetailsService {

    @Resource
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDo account = accountRepository.loadAccount(username);
        Assert.notNull(account, "无法获取账号信息");

       return new UserDetails(){
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return account.getPassword();
            }

            @Override
            public String getUsername() {
                return account.getAccountName();
            }
        };
    }
}
