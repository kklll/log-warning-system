package com.kklll.logwarningsystem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class LogWaringSystemApplicationTests {

    @Test
    void contextLoads() {
        log.debug("{} OK", "logback");
        log.info("{} OK", "logback");
        log.warn("{} Not OK", "logback");
    }

}
