package com.study.reactJava.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class AESUtilTest {


    @Test
    void generateKeyTest() {

        String encrypted = AESUtil.encrypt("mima", "4e6eef02bcb10db096429f0317bb068287295a3d246872d8eeabc71bdb3c9041", "1");

        String decrypt = AESUtil.decrypt(encrypted, "4e6eef02bcb10db096429f0317bb068287295a3d246872d8eeabc71bdb3c9041", "1");

        Assertions.assertEquals("mima", decrypt);
    }


}

