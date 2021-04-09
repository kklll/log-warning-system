package com.kklll.logwarningsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kklll.logwarningsystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/3/15 15:55
 * @Version 1.0
 **/

@Mapper
public interface UserMapper extends BaseMapper<User> {
}