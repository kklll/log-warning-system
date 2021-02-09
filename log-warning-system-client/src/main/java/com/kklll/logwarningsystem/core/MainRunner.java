package com.kklll.logwarningsystem.core;

import com.kklll.logwarningsystem.core.config.LogConfig;
import com.kklll.logwarningsystem.core.interfaces.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @ClassName MainRunner
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/19 21:54
 * @Version 1.0
 **/
@Service
@Slf4j
public class MainRunner implements ApplicationRunner {
    @Autowired
    Server mainServer;

    /**
     * @author DeepBlue
     * @date: 2021/2/9 15:26
     * @description: 主程序
     */
    @Override
    public void run(ApplicationArguments args) {
        mainServer.serve();
    }
}
