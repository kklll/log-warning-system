package com.kklll.logwarningsystem.core.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName LogConfig
 * @Deacription 要发往下一个系统进行处理的日志配置类，主要包括服务名称，日志的路径，日志文件编码格式，日志文件名称等
 * @Author DeepBlue
 * @Date 2021/2/8 22:31
 * @Version 1.0
 **/
@Configuration
@Data
@ToString
public class LogConfig {
    @Value("${spring.application.name}")
    private String serverName;
    @Value("${log.path}")
    private String logPath;
    @Value("${log.filename}")
    private String filename;
    @Value("${log.fileEncoding}")
    private String fileEncoding;
}
