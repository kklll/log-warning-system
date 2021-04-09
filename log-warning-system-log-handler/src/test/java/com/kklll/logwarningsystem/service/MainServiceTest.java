package com.kklll.logwarningsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kklll.logwarningsystem.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName MainServiceTest
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/3 23:00
 * @Version 1.0
 **/
@SpringBootTest
class MainServiceTest {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    void testCreateIndex() {
        //创建索引请求
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("origin", "127.0.0.1");
        jsonMap.put("serverName", "nginx1");
        jsonMap.put("time", new Date());
        jsonMap.put("logType", "info");
        jsonMap.put("content", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("logs").source(jsonMap);
        IndexResponse index = null;
        try {
            index = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(index.toString());
    }

    @Test
        //判断索引是否存在
    void testSelectIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("school");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if (exists) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("posts");
        client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
    }

    @Test
    void testAddDocument() {
        //创建对象
        User user = new User();
        user.setAge(12);
        user.setName("李凯");
        //创建请求
        ObjectMapper objectMapper = new ObjectMapper();
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        try {
            indexRequest.source(objectMapper.writeValueAsString(user), XContentType.JSON);
            IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
//获取是否存在且获取文档
    void testIsExist() throws IOException {
        GetRequest getRequest = new GetRequest("user", "1");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (exists) {
            GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println(documentFields.getSourceAsMap());
        }
    }

    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("user", "1");
        updateRequest.timeout("1s");
        //创建对象
        User user = new User();
        user.setAge(15);
        user.setName("李凯凯");
        //创建请求
        ObjectMapper objectMapper = new ObjectMapper();
        updateRequest.doc(objectMapper.writeValueAsString(user), XContentType.JSON);
        UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.toString());
    }

    @Test
    void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("user", "1");
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.toString());
    }

    @Test
    void loadManyData() throws IOException {
        BulkRequest bulkRequest = new BulkRequest("user");
        bulkRequest.timeout("10s");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("xx", 12));
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(new IndexRequest("user").id("" + i + 1).source(objectMapper.writeValueAsString(users.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.toString());
    }


    @Test
    void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("user");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "李凯凯");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.matchAllQuery("name", "李凯凯");
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("name", "李凯");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    void testTime() {

    }

    @Test
    void testSqlInjection()  {
        Connection con;
        String driver = "com.mysql.cj.jdbc.Driver";
        //这里数据库名为mydatabase
        String url = "jdbc:mysql://kklll.cn:3306/log_warning_system?characterEncoding=UTF-8&&serverTimezone=Asia/Shanghai&&autoReconnect=true&&failOverReadOnly=false";
        String user = "root";
        String password = "root";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("数据库连接成功");
            }
            String username = "'1' or '1'='1'";
            String pp = "'1' or '1'='1' 2";
            Statement statement = con.createStatement();
            String sql = "select * from user where username=" + username + " and password=" + pp + ";";//表名称为mytable
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            String name;
            while (resultSet.next()) {
                name = resultSet.getString("username");
                System.out.println("用户名：" + name);
            }
            resultSet.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}