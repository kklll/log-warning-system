package com.kklll.logwarningsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;


/**
 * @ClassName Main
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/19 21:38
 * @Version 1.0
 **/

/*
1. 服务发现 Nacos
2. 配置文件
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
