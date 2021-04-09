package com.kklll.logwarningsystem.server.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kklll.logwarningsystem.mapper.UserMapper;
import com.kklll.logwarningsystem.pojo.User;
import com.kklll.logwarningsystem.pojo.UserInfo;
import com.kklll.logwarningsystem.server.interfaces.UserService;
import com.kklll.logwarningsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Deacription 用户模块相关接口
 * @Author DeepBlue
 * @Date 2021/3/15 15:36
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    /**
     * @author DeepBlue
     * @date: 2021/3/15 15:37
     * @description: 用户登录接口
     */
    public Map<String, String> login(User user) {
        //首先进行校验
        Map<String, String> res = new HashMap<>();
        if (user == null) {
            res.put("msg", "请填写您的用户名和密码！");
            return res;
        }
        //从数据库中获取username登入传入的username并且password等于数据库中的password
        User user1 = userMapper.selectOne
                (new QueryWrapper<User>().eq("username",
                        user.getUsername()).eq("password", user.getPassword()));
        if (user1 == null) {
            res.put("msg", "用户名或密码错误！请您检查后重新登录");
        } else {
            HashMap<String, String> info = new HashMap<>();
            info.put("username", user.getUsername());
            String token = jwtUtils.createToken(info);
            res.put("token", token);
        }
        return res;
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("李凯凯");
        userInfo.setAvatar("https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/head.jpg");
        return userInfo;
    }
}
