package com.dreampass.user.service;

import com.dreampass.user.common.PasswordUtils;
import com.dreampass.user.model.UserDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public void register(UserDo user) {
        String encryptedPassword = PasswordUtils.hashPassword(user.getPassword());
        log.debug("加密后密码:{}", encryptedPassword);
        boolean check = PasswordUtils.matches(user.getPassword(), encryptedPassword);
        log.debug("密码是否一致:{}", check);
    }
}
