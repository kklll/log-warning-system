package com.kklll.logwarningsystem.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ServerConfig
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/30 16:57
 * @Version 1.0
 **/
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "server")
public class ServerConfig {
    //服务名称
    private String name;
    //服务描述
    private String description="null";
    //服务IP地址
    private String host;
    //服务端口
    private Integer port;
    //服务地理位置
    private String location="未指定地域";
}
