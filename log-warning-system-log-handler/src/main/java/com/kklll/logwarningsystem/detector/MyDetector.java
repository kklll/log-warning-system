package com.kklll.logwarningsystem.detector;

import com.kklll.logwarningsystem.mapper.AlertMapper;
import com.kklll.logwarningsystem.pojo.Alert;
import com.kklll.logwarningsystem.pojo.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyDetector
 * @Deacription 错误检测器
 * @Author DeepBlue
 * @Date 2021/4/7 16:29
 * @Version 1.0
 **/
@Service
@Slf4j
public class MyDetector implements Detector {
    @Autowired
    AlertMapper alertMapper;

    //检测日志
    @Override
    public Map<String, Object> detectLog(Log log) {
        if (log.getLogType().equals(Detector.ERROR)) {
            Map<String, Object> map = new HashMap<>(8);
            map.put("type", "error");
            map.put("level", "1");
            return map;
        }
        return null;
    }

    @Override
    public void collectInformation(Log log, Map<String, Object> info) {
        Alert alert = new Alert();
        alert.setServername(log.getServerName());
        alert.setAlertname(log.getLogType() + "事件");
        alert.setLevel(1);
        alert.setAlertdate(new Date());
        alert.setHandler(null);
        alert.setContent(log.getContent());
        alert.setState(1);
        alertMapper.insert(alert);
    }
}
