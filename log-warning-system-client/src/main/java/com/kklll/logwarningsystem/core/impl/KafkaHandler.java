package com.kklll.logwarningsystem.core.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.LineHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kklll.logwarningsystem.core.config.ServerConfig;
import com.kklll.logwarningsystem.core.pojo.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName KafkaHandler
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/9 14:06
 * @Version 1.0
 **/
@Slf4j
@Component
public class KafkaHandler implements LineHandler {
    @Value("${kafka.topic}")
    String topic;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    Log logs;

    @Override
    public void handle(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        Log logInfo = new Log();
        logInfo.setServerName(logs.getServerName());
        logInfo.setOrigin(logs.getOrigin());
        if (isError(s)) {
            logInfo.setLogType("Error");
        } else if (isWarning(s)) {
            logInfo.setLogType("Warn");
        } else {
            logInfo.setLogType("Info");
        }
        logInfo.setTime(new Date());
        logInfo.setContent(s);
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(logInfo));
            log.error(objectMapper.writeValueAsString(logInfo));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public boolean isError(String s) {
        if (s.toLowerCase().contains("error")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWarning(String s) {
        if (s.toLowerCase().contains("warn")) {
            return true;
        } else {
            return false;
        }
    }
}
