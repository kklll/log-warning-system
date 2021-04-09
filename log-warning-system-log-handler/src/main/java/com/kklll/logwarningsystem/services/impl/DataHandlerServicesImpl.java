package com.kklll.logwarningsystem.services.impl;

import com.kklll.logwarningsystem.detector.Detector;
import com.kklll.logwarningsystem.pojo.Log;
import com.kklll.logwarningsystem.services.interfaces.DataHandlerService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DataHandlerServicesImpl
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/4/7 17:06
 * @Version 1.0
 **/
@Service
public class DataHandlerServicesImpl implements DataHandlerService {
    private List<Detector> detectors;

    public DataHandlerServicesImpl(ApplicationContext applicationContext) {
        detectors = new ArrayList<>();
        Map<String, Detector> beansOfType =
                applicationContext.getBeansOfType(Detector.class);
        for (Map.Entry<String, Detector> e : beansOfType.entrySet()) {
            System.out.println(e.getValue());
            detectors.add(e.getValue());
        }
    }

    @Override
    public void handleData(Log log) {

    }
}
