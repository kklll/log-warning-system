package com.kklll.logwarningsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author DeepBlue
 * @date: 2021/1/6 15:31
 * @description: 主程序类
 */
@SpringBootApplication
/*
完成的工作：
1.服务发现，让web可以感知到有多少个服务在线
2.扫描日志文件
3.发送日志到KAKFA消息队列
4.应用接口
5.
 */
public class LogWaringSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogWaringSystemApplication.class, args);
    }
}