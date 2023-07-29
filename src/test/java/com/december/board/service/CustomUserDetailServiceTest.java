package com.december.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserDetailServiceTest {

    @Autowired
    CustomUserDetailService customUserDetailService;
    @Test
    void loadUserByUsername() {
//        System.out.println(customUserDetailService.loadUserByUsername("user"));
    }
}