package com.kklll.logwarningsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kklll.logwarningsystem.pojo.*;
import com.kklll.logwarningsystem.server.interfaces.DataService;
import com.sun.istack.internal.NotNull;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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
            HighlightField content = hit.getHighlightFields().get("content");
            String sourceAsMap = hit.getSourceAsString();
            Log log = null;
            try {
                log = objectMapper.readValue(sourceAsMap, Log.class);
                if (content != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Text s : content.getFragments()) {
                        sb.append(s.string());
                    }
                    log.setContent(sb.toString());
                }
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

    /**
     * @author DeepBlue
     * @date: 2021/4/13 16:53
     * @description: 获取告警信息
     */
    @GetMapping("getAlert")
    public ResultBody getAlert() {
        List<Alert> alert = dataService.getAlert();
        return ResultBody.success(alert);
    }

    /**
     * @author DeepBlue
     * @date: 2021/4/13 16:53
     * @description: 进行告警处理时将告警状态进行更新
     */
    @PostMapping("updateAlert")
    public ResultBody updateAlert(@NotNull @RequestBody Alert alert, @RequestHeader("token") String token) {
        int i = dataService.updateAlert(alert, token);
        if (i >= 1) {
            return ResultBody.success();
        } else {
            return ResultBody.error("更新失败");
        }
    }

    /**
     * @author DeepBlue
     * @date: 2021/4/13 16:54
     * @description: 忽略告警信息后更新告警状态
     */
    @PostMapping("ignoreAlert")
    public ResultBody ignoreAlert(@NotNull @RequestBody Alert alert, @RequestHeader("token") String token) {
        int i = dataService.ignoreAlert(alert, token);
        if (i >= 1) {
            return ResultBody.success();
        } else {
            return ResultBody.error("更新失败");
        }
    }

    /**
     * @author DeepBlue
     * @date: 2021/4/14 9:48
     * @description: 报警事件条件查询
     */
    @PostMapping("alertSearch")
    public ResultBody alertSearch(@NotNull @RequestBody AlertSearchCondition condition){
        Map<String,Object> map= dataService.alertSearch(condition);
        return ResultBody.success(map);
    }

    @PostMapping("handleAlert")
    public ResultBody handleAlert(@NotNull @RequestBody Alert alert, @RequestHeader("token") String token){
        int i = dataService.handleAlert(alert, token);
        if (i >= 1) {
            return ResultBody.success();
        } else {
            return ResultBody.error("更新失败");
        }
    }
}
