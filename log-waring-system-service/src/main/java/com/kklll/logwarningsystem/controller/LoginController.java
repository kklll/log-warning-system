package com.kklll.logwarningsystem.controller;

import com.kklll.logwarningsystem.pojo.ResultBody;
import com.kklll.logwarningsystem.pojo.User;
import com.kklll.logwarningsystem.server.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName LoginController
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/15 14:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("login")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping("login")
    public ResultBody login(@RequestBody User user) {
        Map<String, String> login = userService.login(user);
        return ResultBody.success(login);
    }

}
