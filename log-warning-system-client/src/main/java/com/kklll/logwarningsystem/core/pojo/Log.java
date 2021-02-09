package com.kklll.logwarningsystem.core.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Log
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/8 22:22
 * @Version 1.0
 **/
@Data
public class Log {
    private String origin;
    private String serverName;
    private LocalDateTime time;
    private String content;
}
