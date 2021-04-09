package com.kklll.logwarningsystem.services.interfaces;

import com.kklll.logwarningsystem.pojo.Log;
import org.springframework.stereotype.Service;

/**
 * @ClassName Service
 * @Deacription 本服务的服务接口, 主要描述本服务应该实现哪些功能
 * @Author DeepBlue
 * @Date 2021/4/7 16:36
 * @Version 1.0
 **/
@Service
public interface MainService {
    /**
     * @author DeepBlue
     * @date: 2021/4/7 16:38
     * @description: 日志收集
     */
    void collectionLogs(String msg);

    /**
     * @author DeepBlue
     * @date: 2021/4/7 16:39
     * @description: 进行日志检测的任务
     */
    void detectLogs(String log);
}
