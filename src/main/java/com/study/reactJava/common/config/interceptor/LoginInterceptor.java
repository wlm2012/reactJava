package com.study.reactJava.common.config.interceptor;

import com.study.reactJava.common.CommonDO.exception.ServiceException;
import com.study.reactJava.infrastructure.utils.JwtInfraUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtInfraUtil jwtInfraUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果本次请求是登录，就直接放行
        String uri = request.getRequestURI();
        if ("/login".equals(uri)) {
            log.info("本次请求是登录，直接放行");
            return true;
        }
        // 获取本次请求携带的token
        String token = request.getHeader("Authorization");
        //   如果没有token：直接返回响应结果
        if (!StringUtils.hasText(token)) {
            throw new ServiceException("请求失败，未登录");
        }
        //   如果token过期或非法：直接返回响应结果
        try {
            jwtInfraUtil.validateToken(token);
        } catch (Exception e) {
            throw new ServiceException("请求失败，未登录");
        }
        //3. 放行
        return true;
    }
}