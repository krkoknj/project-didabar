package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.presistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class EmailConfigControllerTest {

    UserRepository userRepository;


    @Test
    public void test() {
        System.out.println("userRepository = " + userRepository);
    }

}