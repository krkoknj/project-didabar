package com.bitcamp221.didabara;


import com.bitcamp221.didabara.presistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 테스트() {
        System.out.println("userRepository = " + userRepository);
    }


}
