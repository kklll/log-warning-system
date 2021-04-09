package com.kklll.logwarningsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kklll.logwarningsystem.pojo.Alert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName AlertMapper
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/7 16:08
 * @Version 1.0
 **/

@Mapper
public interface AlertMapper extends BaseMapper<Alert> {
}