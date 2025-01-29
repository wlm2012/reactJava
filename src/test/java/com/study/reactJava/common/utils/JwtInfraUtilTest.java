package com.study.reactJava.common.utils;

import com.study.reactJava.infrastructure.utils.JwtInfraUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class JwtInfraUtilTest {

    private final JwtInfraUtil jwtInfraUtil;

    @Test
    void createToken() {
        String token = jwtInfraUtil.createToken("1234");
        System.out.println("token = " + token);
    }

}