package com.kklll.logwarningsystem.server.interfaces;

import com.kklll.logwarningsystem.pojo.SearchCondition;
import org.elasticsearch.action.search.SearchResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName DataService
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/4 21:11
 * @Version 1.0
 **/
public interface DataService {
    /**
     * @author DeepBlue
     * @date: 2021/4/4 21:13
     * @description: 获取统计信息,其中包括对error的个数,info的个数,警告的个数进行统计
     */
    List<LinkedList<Object>> getStatistics();

    /**
     * @author DeepBlue
     * @date: 2021/4/5 21:40
     * @description: 获取一些查询参数,返回查询结果
     */
    SearchResponse getSearchInfo(SearchCondition searchCondition);
}
