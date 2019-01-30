package com.rrd.teg.optimus.runner;

import com.google.common.collect.Queues;
import com.rrd.teg.optimus.config.pool.PooledWebDriverFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project spider
 * @package com.rrd.teg.runner
 * @since 2019-01-22
 */
@Component
public class Init {

    private static final Logger logger = LoggerFactory.getLogger(Init.class);

    @Autowired
    private GenericObjectPool<WebDriver> genericObjectPool;

    @Async
    public void initPool() {
        // 线程池init
        WebDriver webDriver = null;
        try {
            webDriver = genericObjectPool.borrowObject();
        } catch (Exception e) {
        } finally {
            genericObjectPool.returnObject(webDriver);
        }
    }

    public static void initProxy() {
        String str;
        try (FileReader fr = new FileReader(System.getProperty("user.dir") + File.separator + "XCProxy.txt");
             BufferedReader bf = new BufferedReader(fr);) {
            // 按行读取字符串
            Queue<String> queue = Queues.newConcurrentLinkedQueue();
            while ((str = bf.readLine()) != null) {
                queue.add(str);
            }
            PooledWebDriverFactory.queue = queue;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
