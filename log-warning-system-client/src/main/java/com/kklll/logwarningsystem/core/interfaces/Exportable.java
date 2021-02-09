package com.kklll.logwarningsystem.core.interfaces;


import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @ClassName Exportable
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/8 22:17
 * @Version 1.0
 **/
@Component
public interface Exportable {

    /**
     * @author DeepBlue
     * @date: 2021/2/9 15:18
     * @description: 获取日志文件数组
     */
    File[] loadFile(String fileName, String filePath);

    /**
     * @author DeepBlue
     * @date: 2021/2/9 15:18
     * @description: 读取日志文件的变动并且将变动发送到kafka消息队列
     */
    void readUpdateAndSend(File[] file,Charset charset);
}
