package com.rrd.teg.optimus.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author gengshikun
 * @date 2016/11/25
 */
@Component
public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    private Init init;

    /**
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) {
        // 线程池init
        for (int i = 0; i < 8; i++) {
            init.initPool();
        }
    }


}
