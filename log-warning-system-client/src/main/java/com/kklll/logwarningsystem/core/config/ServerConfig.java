package com.kklll.logwarningsystem.core.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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
public class ServerConfig {
    //服务名称
    private String name;
    //服务描述
    private String description;
    //日志路径
    private String logDir;
    //远程服务Ip地址(KAFKA消息队列)
    private String remoteAddress;
    //远程服务端口(KAFKA端口)
    private String remotePort;

}
