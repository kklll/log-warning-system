package com.kklll.logwarningsystem.core.impl;

import com.kklll.logwarningsystem.core.config.LogConfig;
import com.kklll.logwarningsystem.core.interfaces.Exportable;
import com.kklll.logwarningsystem.core.interfaces.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @ClassName MainServer
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/2 17:05
 * @Version 1.0
 **/
@Component
public class MainServer implements Server {
    @Autowired
    LogConfig logConfig;
    @Autowired
    Exportable exportable;

    @Override
    public void serve() {
        File[] files = exportable.loadFile(logConfig.getFilename(), logConfig.getLogPath());
        exportable.readUpdateAndSend(files, Charset.forName(logConfig.getFileEncoding()));
    }
}
