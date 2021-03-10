package com.kklll.logwaringsystem.server.interfaces;

import com.kklll.logwaringsystem.pojo.ServiceInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ServicesServcer
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/9 19:15
 * @Version 1.0
 **/
public interface ServicesServer {

    /**
     * @author DeepBlue
     * @date: 2021/3/10 17:25
     * @description: 获取当前在线的服务
     */
    public List<ServiceInfo> getOnlineServices();
    /**
     * @author DeepBlue
     * @date: 2021/3/10 17:25
     * @description: 获取当前所有的服务
     */
    public List<ServiceInfo> getAllServices();
}
