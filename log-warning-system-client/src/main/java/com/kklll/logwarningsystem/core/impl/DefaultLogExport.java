package com.kklll.logwarningsystem.core.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.file.Tailer;
import com.kklll.logwarningsystem.core.interfaces.Exportable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @ClassName WindowsLogExport
 * @Deacription Windows上的进行文件上传和发现的接口
 * @Author DeepBlue
 * @Date 2021/2/8 22:20
 * @Version 1.0
 **/
@Component
@Slf4j
public class DefaultLogExport implements Exportable {
    @Autowired
    LineHandler lineHandler;

    @Override
    public File[] loadFile(String fileName, String filePath) {
        //注意这里的filename可能包含多个文件，放在相同的path下
        String[] split = fileName.split(";");
        File[] files = new File[split.length];
        for (int i = 0; i < split.length; i++) {
            File f = new File(filePath, split[i]);
            if (!f.exists()) {
                log.error("文件{}不存在,请检查文件路径！", f.getName());
                System.exit(1);
            }
            files[i] = f;
        }
        return files;
    }

    @Override
    public void readUpdateAndSend(File[] file, Charset charset) {
        //为每个文件创建一个线程，进行文件的读取
        for (File value : file) {
            //KafkaHandler是使用此handler进行文件内容的发送，具体请查看其实现
            Tailer tailer = new Tailer(FileUtil.file(value), charset, lineHandler, 1, 2);
            tailer.start(true);
        }
    }
}
