package com.kklll.logwarningsystem.core.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @ClassName Log
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/8 22:22
 * @Version 1.0
 **/
@Data
@Configuration
public class Log {
    @Value("${server.host}")
    private String origin;
    @Value("${server.name}")
    private String serverName;
    private Date time;
    private String logType;
    private String content;
}

