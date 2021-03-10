package com.kklll.logwaringsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ServiceInfo
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/9 19:16
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfo {
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
