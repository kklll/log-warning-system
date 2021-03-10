package com.kklll.logwaringsystem.controller;

import com.kklll.logwaringsystem.pojo.ServiceInfo;
import com.kklll.logwaringsystem.server.interfaces.ServicesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ServicesController
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/9 19:14
 * @Version 1.0
 **/
@RestController
@RequestMapping("services")
public class ServicesController {

    @Autowired
    ServicesServer server;

    @GetMapping("onlineServices")
    public List<ServiceInfo> getOnlineServices() {
        return server.getOnlineServices();
    }

    @GetMapping("allServices")
    public List<ServiceInfo> getAllServices() {
        return server.getAllServices();
    }
}
