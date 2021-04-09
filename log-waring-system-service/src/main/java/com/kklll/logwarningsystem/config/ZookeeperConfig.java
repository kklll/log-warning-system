package com.kklll.logwarningsystem.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName ZookeeperConfig
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/9 21:11
 * @Version 1.0
 **/
@Configuration
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "zk")
public class ZookeeperConfig {
    private String address;

    private int timeout;

    private String rootNode;


    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.error("【初始化ZooKeeper连接状态....】={}", zooKeeper.getState());
        } catch (Exception e) {
            log.error("初始化ZooKeeper连接异常....】={}", e.getMessage());
            System.exit(1);
        }
        return zooKeeper;
    }
}
