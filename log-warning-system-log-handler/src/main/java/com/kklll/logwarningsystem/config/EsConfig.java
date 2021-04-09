package com.kklll.logwarningsystem.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @ClassName ESConfig
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/3 23:32
 * @Version 1.0
 **/
@Configuration
public class EsConfig {
    @Value("${es.host}")
    private String host;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] split = host.split(";");
        RestClientBuilder http = null;
        if (split.length > 1) {
            for (int i = 0; i < split.length; i++) {
                http = RestClient.builder(new HttpHost(split[i].split(":")[0], Integer.parseInt(split[i].split(":")[1])));
            }
        } else {
            http = RestClient.builder(new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1])));
        }
        RestHighLevelClient client = new RestHighLevelClient(http);
        return client;

    }
}
