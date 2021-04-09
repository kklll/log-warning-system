package com.kklll.logwarningsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @ClassName WebConfig
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/6 15:32
 * @Version 1.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*")
                .allowCredentials(true).allowedHeaders("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS").maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                authInterceptor).addPathPatterns("/**").excludePathPatterns("/login/**").excludePathPatterns("/test/**");
    }
}
