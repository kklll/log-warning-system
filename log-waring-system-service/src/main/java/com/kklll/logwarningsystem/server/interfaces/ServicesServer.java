package com.kklll.logwarningsystem.server.interfaces;

import com.kklll.logwarningsystem.pojo.ServiceInfo;

import java.util.List;

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

    /**
     * @author DeepBlue
     * @date: 2021/3/23 19:13
     * @description: 删除服务信息
     */
    boolean deleteService(String nodeName);
}
