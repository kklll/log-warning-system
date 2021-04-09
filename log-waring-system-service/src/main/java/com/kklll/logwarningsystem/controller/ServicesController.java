package com.kklll.logwarningsystem.controller;

import com.kklll.logwarningsystem.pojo.ResultBody;
import com.kklll.logwarningsystem.server.interfaces.ServicesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResultBody getOnlineServices() {
        return ResultBody.success(server.getOnlineServices());
    }

    @GetMapping("allServices")
    public ResultBody getAllServices() {
        return ResultBody.success(server.getAllServices());
    }

    //删除节点
    @PostMapping("deleteServices")
    public ResultBody deleteServices(@RequestBody Map<String, String> u) {
        if (server.deleteService(u.get("nodeName"))) {
            return ResultBody.success();
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("msg", "节点处于活跃状态，无法删除！");
            return ResultBody.success(map);
        }
    }
}
