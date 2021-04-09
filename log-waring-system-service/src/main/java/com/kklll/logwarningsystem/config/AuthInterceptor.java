package com.kklll.logwarningsystem.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kklll.logwarningsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthInterceptor
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/15 14:54
 * @Version 1.0
 **/
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtils jwtUtils;
    private static final String OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (OPTIONS.equals(request.getMethod())) {
            return true;
        }
        try {
            String requestToken = jwtUtils.getRequestToken(request);
            jwtUtils.verify(requestToken);
        } catch (JWTVerificationException | NullPointerException e) {
            try {
                response.sendError(403);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
