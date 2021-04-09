package com.kklll.logwarningsystem.detector;

import com.kklll.logwarningsystem.pojo.Log;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName XssDetector
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/7 17:15
 * @Version 1.0
 **/
@Service
public class XssDetector implements Detector{
    @Override
    public Map<String, Object> detectLog(Log log) {
        return null;
    }

    @Override
    public void collectInformation(Log log, Map<String, Object> info) {

    }
}
