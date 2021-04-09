package com.kklll.logwarningsystem.detector;

import com.kklll.logwarningsystem.pojo.Log;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName SqlInjectionDetector
 * @Deacription SQL注入检测器
 * @Author DeepBlue
 * @Date 2021/4/7 16:29
 * @Version 1.0
 **/
@Service
public class SqlInjectionDetector implements Detector {
    @Override
    public Map<String, Object> detectLog(Log log) {
        return null;
    }

    @Override
    public void collectInformation(Log log, Map<String, Object> info) {

    }
}
