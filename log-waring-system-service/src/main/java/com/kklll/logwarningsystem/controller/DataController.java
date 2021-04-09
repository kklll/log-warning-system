package com.kklll.logwarningsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kklll.logwarningsystem.pojo.Log;
import com.kklll.logwarningsystem.pojo.ResultBody;
import com.kklll.logwarningsystem.pojo.SearchCondition;
import com.kklll.logwarningsystem.server.interfaces.DataService;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName DataController
 * @Deacription 请求数据的接口
 * @Author DeepBlue
 * @Date 2021/4/4 20:55
 * @Version 1.0
 **/
@RestController
@RequestMapping("data")
public class DataController {
    @Autowired
    DataService dataService;


    /**
     * @author DeepBlue
     * @date: 2021/4/7 13:24
     * @description: 获取统计信息，包括每个时间段的的Info，Warn，Error级别的日志的数量
     */
    @GetMapping("getStatistics")
    public ResultBody getStatistics() {
        List<LinkedList<Object>> statistics = dataService.getStatistics();
        return ResultBody.success(statistics);
    }

    /**
     * @author DeepBlue
     * @date: 2021/4/7 13:25
     * @description: 获取日志查询的信息，其中SearchCondition是查询条件的集合
     */
    @PostMapping("getSearchInfo")
    public ResultBody getSearchInfo(@RequestBody SearchCondition condition) {
        Map<String, Object> res = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        SearchResponse search = dataService.getSearchInfo(condition);
        TotalHits totalHits = search.getHits().getTotalHits();
        res.put("total", totalHits.value);
        List<Log> list = new ArrayList<>();
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsMap = hit.getSourceAsString();
            Log log = null;
            try {
                log = objectMapper.readValue(sourceAsMap, Log.class);
                //矫正时区
                log.setTime(new Date(log.getTime().getTime() + 8 * 60 * 60 * 1000));
                list.add(log);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        res.put("data", list);
        return ResultBody.success(res);
    }
}
