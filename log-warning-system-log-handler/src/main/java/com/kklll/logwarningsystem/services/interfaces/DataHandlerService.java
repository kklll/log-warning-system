package com.kklll.logwarningsystem.services.interfaces;

import com.kklll.logwarningsystem.pojo.Log;
import org.springframework.stereotype.Service;

/**
 * @ClassName DataHandlerService
 * @Deacription 数据处理接口
 * @Author DeepBlue
 * @Date 2021/4/7 16:12
 * @Version 1.0
 **/
@Service
public interface DataHandlerService {
    /**
     * @author DeepBlue
     * @date: 2021/4/7 16:55
     * @description: 对数据进行处理
     */
    void handleData(Log log);
}
