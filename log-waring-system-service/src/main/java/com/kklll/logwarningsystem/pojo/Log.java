package com.kklll.logwarningsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName Log
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/8 22:22
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String origin;
    private String serverName;
    private Date time;
    private String logType;
    private String content;
}
