package com.kklll.logwarningsystem.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kklll.logwarningsystem.detector.Detector;
import com.kklll.logwarningsystem.pojo.Log;
import com.kklll.logwarningsystem.services.interfaces.DataHandlerService;
import com.kklll.logwarningsystem.services.interfaces.MainService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class MainServiceImpl implements MainService {
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    @Value("${es.index}")
    private String index;

    @Autowired
    DataHandlerService dataHandlerService;

    @KafkaListener(topics = "${kafka.topic}")
    public void onMessage(String message) {
        System.err.println(message);
        collectionLogs(message);
        detectLogs(message);
    }


    @Override
    public void collectionLogs(String message) {
        IndexRequest indexRequest = new IndexRequest(index);
        try {
            indexRequest.source(message, XContentType.JSON);
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detectLogs(String log) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Log log1 = objectMapper.readValue(log, Log.class);
            dataHandlerService.handleData(log1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
