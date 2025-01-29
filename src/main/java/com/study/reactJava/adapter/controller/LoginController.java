package com.study.reactJava.adapter.controller;

import com.study.reactJava.application.dto.request.LoginReq;
import com.study.reactJava.application.service.impl.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginReq loginReq) {
        return loginService.login(loginReq);
    }


}

