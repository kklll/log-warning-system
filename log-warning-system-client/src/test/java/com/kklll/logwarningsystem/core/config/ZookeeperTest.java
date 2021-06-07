package com.kklll.logwarningsystem.core.config;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ZookeeperTest
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/18 15:24
 * @Version 1.0
 **/
@SpringBootTest
public class ZookeeperTest {
    @Autowired
    ZooKeeper zooKeeper;

    @Test
    public void testZk() {
        try {
            List<String> children = zooKeeper.getChildren("/log-service/online", true);
            //在线主机名称集合
            List<String> hostNames = new ArrayList<>();
            for (String c : children) {
                hostNames.add(c);
            }
            System.out.println("所有在线的HostName" + hostNames);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
