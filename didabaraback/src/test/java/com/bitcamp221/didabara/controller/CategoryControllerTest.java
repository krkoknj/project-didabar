package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.model.BaseTimeEntity;
import com.bitcamp221.didabara.model.CategoryEntity;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.CategoryRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.*;

@EnableJpaAuditing
@SpringBootTest
class CategoryControllerTest extends BaseTimeEntity {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    void saveUser() {
        for (int i = 0; i <= 100; i++) {
            UserEntity user = UserEntity.builder()
                    .id((long) i)
                    .password("12345" + i)
                    .nickname("abc" + i)
                    .username("aaa@abc.com"+i)
                    .build();

            userRepository.save(user);
        }
    }
}