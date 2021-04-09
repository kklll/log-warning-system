package com.kklll.logwarningsystem.server.interfaces;


import com.kklll.logwarningsystem.pojo.User;
import com.kklll.logwarningsystem.pojo.UserInfo;

import java.util.Map;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 16:19
 */
public interface UserService {
    /**
     * 登录接口
     *
     * @param user
     * @return
     */
    Map<String,String> login(User user);


    UserInfo getUserInfo();
}
