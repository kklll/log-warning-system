package com.kklll.logwarningsystem.server.Imp;

import com.kklll.logwarningsystem.server.interfaces.DataService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * @ClassName DataServiceImplTest
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/4 21:34
 * @Version 1.0
 **/
@SpringBootTest
class DataServiceImplTest {
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    @Autowired
    DataService dataService;

    @Test
    void getStatistics() {
        SearchRequest searchRequest = new SearchRequest("logs");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("logType", "Info");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(search.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void rangeSearch() {
        SearchRequest searchRequest = new SearchRequest("logs");
        DateHistogramAggregationBuilder field = AggregationBuilders.dateHistogram("times").field("time")
                .calendarInterval(DateHistogramInterval.MINUTE).timeZone(ZoneId.of("+08:00"))
                .format("yyyy-MM-dd HH:mm:ss").order(BucketOrder.key(false));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("logType", "Error"));
        searchSourceBuilder.aggregation(field);
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = search.getAggregations();
            Histogram histogram = aggregations.get("times");
            int need = 0;
            for (Histogram.Bucket bucket : histogram.getBuckets()) {
                if (need < 10) {
                    // 获取桶的Key值
                    String key = bucket.getKeyAsString();
                    // 获取文档总数
                    long count = bucket.getDocCount();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date parse = simpleDateFormat.parse(key);
                        System.out.println(simpleDateFormat.format(parse));
                        System.out.println(count);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createIndexByType() {

    }

    @Test
    void getSearchInfo() {
        dataService.getSearchInfo(null);
    }
}