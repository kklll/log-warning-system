package com.kklll.logwarningsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kklll.logwarningsystem.exception.CommonEnum;
import com.kklll.logwarningsystem.mapper.UserMapper;
import com.kklll.logwarningsystem.pojo.ResultBody;
import com.kklll.logwarningsystem.pojo.User;
import com.kklll.logwarningsystem.server.interfaces.UserService;
import com.kklll.logwarningsystem.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/6 15:35
 * @Version 1.0
 **/
@RestController
@Api("用户相关API")
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("getUserinfo")
    public ResultBody getUserInfo() {
        return ResultBody.success(userService.getUserInfo());
    }

    @PostMapping("updateUser")
    public ResultBody updateUser(@RequestBody User user){
        return ResultBody.success(userService.updateUser(user));
    }

    @GetMapping("getUser")
    public ResultBody getUser(@RequestHeader("token")String token) {
        String username= jwtUtils.getUsername(token);
        User dbUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        dbUser.setPassword(null);
        return ResultBody.success(dbUser);
    }

    @GetMapping("getAdmins")
    public ResultBody getAdmins() {
        List<User> admins = userMapper.selectList(new QueryWrapper<User>());
        for (User admin : admins) {
            admin.setPassword(null);
        }
        return ResultBody.success(admins);
    }

    @PostMapping("addAdmins")
    public ResultBody addAdmins(@RequestBody User user) {
        user.setPassword("111111");
        userMapper.insert(user);
        return ResultBody.success();
    }
}
