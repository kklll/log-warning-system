package com.kklll.logwarningsystem.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * @ClassName MyLogAppender
 * @Deacription
 * @Author DeepBlue
 * @Date 2021/1/17 10:19
 * @Version 1.0
 **/
public class MyLogAppender extends AppenderBase<ILoggingEvent> {
    private String name;

    @Override
    public void setName(String name) {
        this.name = "kafka-log";
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
//        String level = eventObject.getLevel().toString();
//        String loggerName = eventObject.getLoggerName();
//        String msg = eventObject.getFormattedMessage();
//        String threadName = eventObject.getThreadName();
//        Throwable throwable = eventObject.getThrowableProxy() != null ? ((ThrowableProxy) eventObject.getThrowableProxy()).getThrowable() : null;
//        // todo 这里实现自定义的日志处理逻辑
//        System.out.println(name + ": 自定义 logback appender, threadName: " + threadName + ", level: " + level + ", loggerName: " + loggerName + ", msg: " + msg);
//        if (throwable != null) {
//            throwable.printStackTrace();
//        }
    }
}
