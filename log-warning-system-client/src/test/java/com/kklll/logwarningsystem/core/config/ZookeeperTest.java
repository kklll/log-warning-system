package com.kklll.logwarningsystem.core.config;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
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
    ZooKeeper zooKeeper;

    @Test
    public void testZk() {
        try {
            zooKeeper = new ZooKeeper("kklll.cn", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    try {
                        getChildren();
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", true);
        //主机名称集合
        List<String> hostNames = new ArrayList<>();
        for (String c : children) {
            byte[] data = zooKeeper.getData("/" + c, false, null);
            hostNames.add(c);
        }
        System.out.println("所有在线的HostName" + hostNames);
    }
}
