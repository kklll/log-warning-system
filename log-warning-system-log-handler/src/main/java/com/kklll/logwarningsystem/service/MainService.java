package com.kklll.logwarningsystem.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName MainService
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/20 16:00
 * @Version 1.0
 **/
@Service
public class MainService {
    @KafkaListener(topics = "test")
    public void onMessage(String message) {
        System.out.println("message: " + message);
    }
}
