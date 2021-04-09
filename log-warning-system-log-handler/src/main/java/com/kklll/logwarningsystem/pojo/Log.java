package com.kklll.logwarningsystem.pojo;
import lombok.Data;

import java.util.Date;

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
    private Date time;
    private String logType;
    private String content;
}
