package com.kklll.logwarningsystem.core.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kklll.logwarningsystem.core.config.LogConfig;
import com.kklll.logwarningsystem.core.config.ServerConfig;
import com.kklll.logwarningsystem.core.config.ZookeeperConfig;
import com.kklll.logwarningsystem.core.interfaces.Exportable;
import com.kklll.logwarningsystem.core.interfaces.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName MainServer
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/2 17:05
 * @Version 1.0
 **/
@Component
@Slf4j
public class MainServer implements Server {
    @Autowired
    LogConfig logConfig;
    @Autowired
    Exportable exportable;
    @Autowired
    ServerConfig serverConfig;
    @Autowired
    ZooKeeper zkClient;
    @Autowired
    ZookeeperConfig zkconfig;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void serve() {
        File[] files = exportable.loadFile(logConfig.getFilename(), logConfig.getLogPath());
        exportable.readUpdateAndSend(files, Charset.forName(logConfig.getFileEncoding()));
    }

    @Override
    public void registe() {
        try {
            if (zkClient.exists(zkconfig.getRootNode(), null) == null) {
                zkClient.create(zkconfig.getRootNode(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zkClient.exists(zkconfig.getRootNode() + "/all", null) == null) {
                zkClient.create(zkconfig.getRootNode() + "/all", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zkClient.exists(zkconfig.getRootNode() + "/online", null) == null) {
                zkClient.create(zkconfig.getRootNode() + "/online", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            byte[] bytes = new byte[0];
            try {
                bytes = mapper.writeValueAsBytes(new ServerConfig(
                        serverConfig.getName(), serverConfig.getDescription(), serverConfig.getHost(), serverConfig.getPort(), serverConfig.getLocation()));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
            zkClient.create(zkconfig.getRootNode() + "/all/" + serverConfig.getName(), bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zkClient.create(zkconfig.getRootNode() + "/online/" + serverConfig.getName(), bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            byte[] data = zkClient.getData(zkconfig.getRootNode() + "/online/" + serverConfig.getName(), false, null);
            log.error("服务信息: " + new String(data, StandardCharsets.UTF_8));
            log.error("服务注册完成------------进入服务阶段");
        } catch (KeeperException | InterruptedException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }

    public Stat exists(String path, boolean needWatch) {
        try {
            return zkClient.exists(path, needWatch);
        } catch (Exception e) {
            log.error("【断指定节点是否存在异常】{},{}", path, e);
            System.exit(1);
        }
        return null;
    }
}
