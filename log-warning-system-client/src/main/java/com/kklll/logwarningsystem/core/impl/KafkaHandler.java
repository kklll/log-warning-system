package com.kklll.logwarningsystem.core.impl;

import cn.hutool.core.io.LineHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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

    @Override
    public void handle(String s) {
        log.error(s);
        kafkaTemplate.send(topic, s);
    }
}
