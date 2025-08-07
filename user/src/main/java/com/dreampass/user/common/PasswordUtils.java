package com.dreampass.user.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class PasswordUtils {

    // 至少8位，包含大小写字母、数字和特殊字符
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_\\-*/]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private PasswordUtils() {

    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

    public static boolean isStrong(String password) {
        return pattern.matcher(password).matches();
    }
}