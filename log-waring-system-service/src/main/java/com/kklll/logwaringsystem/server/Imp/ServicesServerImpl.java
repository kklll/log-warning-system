package com.kklll.logwaringsystem.server.Imp;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.kklll.logwaringsystem.config.ZookeeperConfig;
import com.kklll.logwaringsystem.pojo.ServiceInfo;
import com.kklll.logwaringsystem.server.interfaces.ServicesServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ServicesServerImpl
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/9 19:17
 * @Version 1.0
 **/
@Service
@Slf4j
public class ServicesServerImpl implements ServicesServer {
    @Autowired
    ZooKeeper zkClient;
    @Autowired
    ZookeeperConfig zkconfig;

    @Override
    public List<ServiceInfo> getOnlineServices() {
        return getAllServicesWithNode("online");
    }

    @Override
    public List<ServiceInfo> getAllServices() {
        return getAllServicesWithNode("all");
    }

    public List<ServiceInfo> getAllServicesWithNode(String nodeName) {
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        try {
            List<String> children = zkClient.getChildren(zkconfig.getRootNode() + "/" + nodeName, false, null);
            for (String child : children) {
                byte[] data = zkClient.getData(zkconfig.getRootNode() + "/" + nodeName + "/" + child, false, null);
                JsonMapper mapper = new JsonMapper();
                try {
                    ServiceInfo serviceInfo = mapper.readValue(data, ServiceInfo.class);
                    serviceInfos.add(serviceInfo);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            log.error(children.toString());
        } catch (KeeperException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serviceInfos;
    }
}
