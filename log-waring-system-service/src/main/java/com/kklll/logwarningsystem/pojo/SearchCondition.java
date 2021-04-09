package com.kklll.logwarningsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SearchCondition
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/5 21:41
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchCondition {
    private String keyword;
    private List<String> services;
    private List<Date> time;
    private List<String> type;
    private int currPage;
    private int pageSize = 10;
    private int total;
}
