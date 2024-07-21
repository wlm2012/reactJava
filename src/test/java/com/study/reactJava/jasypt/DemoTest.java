package com.study.reactJava.jasypt;

import jakarta.annotation.Resource;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Description.
 */
@Disabled
@SpringBootTest
public class DemoTest {

    @Resource
    private StringEncryptor encryptor;

    @Test
    public void encrypt() {
        String url = encryptor.encrypt("url");

        System.out.println("url = " + url);

        String userName = encryptor.encrypt("url");

        System.out.println("userName = " + userName);

        String password = encryptor.encrypt("url");

        System.out.println("password = " + password);
    }
}