package com.december.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SecurityConfigTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void PasswordEncoderTest() {
        String password = "1234";

        String encodePassword = passwordEncoder.encode(password);

        System.out.println("encodePassword = " + encodePassword);

        assertNotEquals(password, encodePassword);
    }
}