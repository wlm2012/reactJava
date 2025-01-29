package com.study.reactJava.application.service.impl;

import com.study.reactJava.application.dto.request.LoginReq;
import com.study.reactJava.common.CommonDO.exception.ServiceException;
import com.study.reactJava.common.utils.AESUtil;
import com.study.reactJava.domain.entity.UserEntity;
import com.study.reactJava.domain.repository.UserRepository;
import com.study.reactJava.infrastructure.utils.JwtInfraUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl {

    @Value("${aes.key}")
    private String aesKey;

    @Value("${aes.salt}")
    private String salt;

    private final UserRepository userRepository;

    private final JwtInfraUtil jwtInfraUtil;

    public String login(LoginReq loginReq) {

        String password = loginReq.password();
        String encryptPassword = AESUtil.encrypt(password, aesKey, salt);
        UserEntity userEntity = userRepository.findByUsername(loginReq.username());

        if (userEntity == null) {
            throw new ServiceException("用户名不存在");
        }

        if (Objects.equals(userEntity.getPassword(), encryptPassword)) {
            throw new ServiceException("密码有误或用户名有误");
        }

        return jwtInfraUtil.createToken(userEntity.getId());
    }
}
