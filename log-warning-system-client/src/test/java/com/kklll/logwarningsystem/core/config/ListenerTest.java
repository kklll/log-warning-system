package com.kklll.logwarningsystem.core.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName ListenerTest
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/2/2 17:44
 * @Version 1.0
 **/
@SpringBootTest
class ListenerTest {

//    @Test
//    public void test() {
//        Properties properties = new Properties();
//        //kafka集群
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kklll.cn:9092,kklll.cn:9093,kklll.cn:9094");
//        //自动提交
//        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//        //提交的超时事时间，这个配置即1秒提交一次
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//        //反序列化类
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//        //消费者组
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test_group");
//
//        //是否重置offset 可选"latest", "earliest", "none"，前提是要更换消费者组(组名不存在)
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        //创建消费者对象
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
//        //消费者订阅主题
//        consumer.subscribe(Arrays.asList("test"));
//        while (true) {
//            //获取数据
//            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(1000));
//            for (ConsumerRecord<String, String> consumerRecord : poll) {
//                System.out.println(consumerRecord.key());
//                System.out.println(consumerRecord.value());
//            }
//        }
//    }
}