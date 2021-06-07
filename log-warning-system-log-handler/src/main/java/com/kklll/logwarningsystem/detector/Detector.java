package com.kklll.logwarningsystem.detector;

import com.kklll.logwarningsystem.pojo.Log;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName Detector
 * @Deacription 检测器的接口，所有类型的错误检测都必须实现此接口
 * @Author DeepBlue
 * @Date 2021/4/7 16:19
 * @Version 1.0
 **/
@Service
public interface Detector {
    String ERROR="Error";
    String WARN="Warn";
    String INFO="Info";
    /**
     * @author DeepBlue
     * @date: 2021/4/7 16:24
     * @description: 输入为log，输出是否具有异常信息，同时对异常信息进行提取
     */
    Map<String, Object> detectLog(Log log);

    /**
     * @author DeepBlue
     * @date: 2021/4/7 16:26
     * @description: 此方法实现对日志文件的返回结果进行整理
     */
    void collectInformation(Log log,Map<String, Object> info);
}
