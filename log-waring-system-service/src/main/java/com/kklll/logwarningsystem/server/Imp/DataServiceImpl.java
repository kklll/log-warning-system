package com.kklll.logwarningsystem.server.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kklll.logwarningsystem.mapper.AlertMapper;
import com.kklll.logwarningsystem.pojo.Alert;
import com.kklll.logwarningsystem.pojo.AlertSearchCondition;
import com.kklll.logwarningsystem.pojo.Log;
import com.kklll.logwarningsystem.pojo.SearchCondition;
import com.kklll.logwarningsystem.server.interfaces.DataService;
import com.kklll.logwarningsystem.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.LongBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * @ClassName DataServiceImpl
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/4 21:11
 * @Version 1.0
 **/
@Service
@Slf4j
public class DataServiceImpl implements DataService {
    @Autowired
    AlertMapper alertMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    public static final String ALL = "All";

    @Override
    public List<LinkedList<Object>> getStatistics() {
        List<LinkedList<Object>> res = new ArrayList<>();
        Map<String, Object> error = getCount(12, "Error");
        Map<String, Object> warn = getCount(12, "Warn");
        Map<String, Object> info = getCount(12, "Info");
        res.add(new LinkedList<>());
        res.add(new LinkedList<>());
        res.add(new LinkedList<>());
        res.add(new LinkedList<>());
        for (Map.Entry<String, Object> e : error.entrySet()) {
            res.get(0).addFirst(e.getKey());
            res.get(1).addFirst(error.getOrDefault(e.getKey(), 0));
            res.get(2).addFirst(warn.getOrDefault(e.getKey(), 0));
            res.get(3).addFirst(info.getOrDefault(e.getKey(), 0));
        }
        res.get(0).addFirst("times");
        res.get(1).addFirst("错误日志数量");
        res.get(2).addFirst("警告日志数量");
        res.get(3).addFirst("正常日志数量");
        return res;
    }

    @Override
    public SearchResponse getSearchInfo(SearchCondition searchCondition) {
        SearchRequest searchRequest = new SearchRequest("logs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件
        //时间范围过滤
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("time")
                .from(searchCondition.getTime().get(0))
                .to(searchCondition.getTime().get(1)));
        //标签过滤
        if (!searchCondition.getType().contains(ALL)) {
            boolQueryBuilder.must(QueryBuilders.termsQuery("logType.keyword", searchCondition.getType()));
        }
        //服务名称过滤
        if (!searchCondition.getServices().contains(ALL)) {
            boolQueryBuilder.must(QueryBuilders.termsQuery("serverName.keyword", searchCondition.getServices()));
        }
        if (!searchCondition.getKeyword().isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("content", searchCondition.getKeyword()));
        }
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("content");
        searchSourceBuilder.highlighter(highlightBuilder);
        //设置分页
        searchSourceBuilder.from((searchCondition.getCurrPage() - 1) * searchCondition.getPageSize());
        //设置每一页的大小
        searchSourceBuilder.size(searchCondition.getPageSize());
        //按时间进行排序
        searchSourceBuilder.sort("time", SortOrder.DESC);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Alert> getAlert() {
        //从数据库里面查
        QueryWrapper<Alert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        return alertMapper.selectList(queryWrapper);
    }

    @Override
    public int updateAlert(Alert alert, String token) {
        UpdateWrapper<Alert> updateWrapper = new UpdateWrapper<Alert>();
        updateWrapper.eq("id", alert.getId())
                .set("state", 2)
                .set("handleDate", new Date())
                .set("handler", jwtUtils.getUsername(token));
        return alertMapper.update(null, updateWrapper);
    }

    @Override
    public int ignoreAlert(Alert alert, String token) {
        UpdateWrapper<Alert> updateWrapper = new UpdateWrapper<Alert>();
        updateWrapper.eq("id", alert.getId())
                .set("state", 4)
                .set("handleDate", new Date())
                .set("handler", jwtUtils.getUsername(token));
        return alertMapper.update(null, updateWrapper);
    }

    @Override
    public int handleAlert(Alert alert, String token) {
        UpdateWrapper<Alert> updateWrapper = new UpdateWrapper<Alert>();
        updateWrapper.eq("id", alert.getId())
                .set("state", 3)
                .set("handleDate", new Date())
                .set("handler", jwtUtils.getUsername(token));
        return alertMapper.update(null, updateWrapper);
    }

    @Override
    public Map<String, Object> alertSearch(AlertSearchCondition condition) {
        //构造查询条件
        QueryWrapper<Alert> queryWrapper = new QueryWrapper<>();
        if (!condition.getServices().contains("All")) {
            queryWrapper.in("serverName", condition.getServices());
        }
        if (!condition.getLevel().contains(0)) {
            queryWrapper
                    .in("level", condition.getLevel());
        }
        if (!condition.getState().contains(0)) {
            queryWrapper
                    .in("state", condition.getState());
        }
        queryWrapper
                .between("alertDate", condition.getTime().get(0), condition.getTime().get(1))
                .orderBy(true, false, "id")
                .like("content", condition.getKeyword());
        Integer count = alertMapper.selectCount(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("res", alertMapper.selectPage(new Page<>(condition.getCurrPage(), condition.getPageSize(), true)
                , queryWrapper));
        return map;
    }


    /**
     * @author DeepBlue
     * @date: 2021/5/13 10:24
     * @description: 获取各个时间段的日志数据
     */
    private TreeMap<String, Object> getCount(int limit, String type) {
        TreeMap<String, Object> res = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return (int) (fmt.parse(o2).getTime() - fmt.parse(o1).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_MONTH, -1);
        SearchRequest searchRequest = new SearchRequest("logs");
        DateHistogramAggregationBuilder field = AggregationBuilders.dateHistogram("times").field("time")
                .calendarInterval(DateHistogramInterval.HOUR).timeZone(ZoneId.of("+08:00"))
                .format("yyyy-MM-dd HH:mm:ss").order(BucketOrder.key(false))
                .extendedBounds(new LongBounds(instance.getTime().getTime(),System.currentTimeMillis()));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("logType", type));
        searchSourceBuilder.aggregation(field);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = search.getAggregations();
            Histogram histogram = aggregations.get("times");
            int need = 0;
            for (Histogram.Bucket bucket : histogram.getBuckets()) {
                if (need < limit) {
                    // 获取桶的Key值
                    String key = bucket.getKeyAsString();
                    // 获取文档总数
                    long count = bucket.getDocCount();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date parse = simpleDateFormat.parse(key);
                        String format = simpleDateFormat.format(parse);
                        res.put(format, count);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    need++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
