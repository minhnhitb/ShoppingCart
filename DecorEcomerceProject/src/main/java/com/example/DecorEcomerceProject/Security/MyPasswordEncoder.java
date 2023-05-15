package com.example.DecorEcomerceProject.Security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPasswordEncoder {
    private final PasswordEncoder passwordEncoder;

    public MyPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
