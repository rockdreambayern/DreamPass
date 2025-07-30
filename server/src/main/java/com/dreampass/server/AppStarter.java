package com.dreampass.server;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dreampass"})
@MapperScan("com.dreampass.dao.mapper")
@Slf4j
public class AppStarter {

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
        log.info("加密算法:{}", System.getProperty("jasypt.encryptor.algorithm"));

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("yourSecretKey"); // 与启动参数保持一致
        String encrypted = encryptor.encrypt("123456@123");
        System.out.println("ENC(" + encrypted + ")");
    }
}
