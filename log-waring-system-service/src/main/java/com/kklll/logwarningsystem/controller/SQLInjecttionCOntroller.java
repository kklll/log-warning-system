package com.kklll.logwarningsystem.controller;

import com.kklll.logwarningsystem.pojo.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SQLInjecttionCOntroller
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/9 9:21
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("test")
public class SQLInjecttionCOntroller {
    @GetMapping("sqlin")
    public ResultBody sqlinjection(String username,String pp) {
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
            Statement statement = con.createStatement();
            String sql = "select * from user where username='" + username + "' and password='" + pp + "';";//表名称为mytable
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            String name;
            List<String> list=new ArrayList<>();
            while (resultSet.next()) {
                name = resultSet.getString("username");
                list.add(name);
            }
            resultSet.close();
            con.close();
            return ResultBody.success(list);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
