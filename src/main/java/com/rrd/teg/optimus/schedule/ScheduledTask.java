package com.rrd.teg.optimus.schedule;

import com.rrd.teg.optimus.runner.Init;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


@Component
@EnableScheduling
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private Init init;

    @Autowired
    private GenericObjectPool<WebDriver> genericObjectPool;

    //    @Scheduled(cron = "0 0/1 * * * ?")
    public void test() {
        List<String> list = Arrays.asList("鲜花", "培训", "人人贷", "好用", "挖掘机", "鲜花", "培训", "人人贷", "好用", "挖掘机", "鲜花", "培训", "人人贷", "好用", "挖掘机");
        list.stream().parallel().forEach(item -> {
            WebDriver webDriver = null;
            try {
                webDriver = genericObjectPool.borrowObject();
                System.err.println("当前线程池活跃对象数：" + genericObjectPool.getNumActive());
                test(webDriver, item);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                genericObjectPool.returnObject(webDriver);
            }
        });
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void spiderProxy() {
        String initUrl = "https://www.xicidaili.com/wt/";
        WebDriver webDriver = null;
        try {
            webDriver = genericObjectPool.borrowObject();

            webDriver.get(initUrl);

            List<WebElement> ips = webDriver.findElements(By.xpath("//tr/td[2]"));
            List<WebElement> ports = webDriver.findElements(By.xpath("//tr/td[3]"));
            List<WebElement> type = webDriver.findElements(By.xpath("//tr/td[6]"));

            try (FileWriter fw = new FileWriter(System.getProperty("user.dir") + File.separator + "XCProxy.txt");
                 BufferedWriter bw = new BufferedWriter(fw);) {
                IntStream.range(0, ips.size()).forEach(index -> {

                    if (this.verifyProxyIP(ips.get(index).getText(), Integer.parseInt(ports.get(index).getText()))) {
                        String str = type.get(index).getText() + "://" + ips.get(index).getText() + ":" + ports.get(index).getText();
                        try {
                            bw.write(str);
                            bw.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            genericObjectPool.clear();
            init.initPool();
        }
    }

    public static boolean verifyProxyIP(String ip, Integer port) {
        Socket server = new Socket();
        try {
            InetSocketAddress address = new InetSocketAddress(ip, port);
            server.connect(address, 3000);
            logger.info("此ip可用. ip:{}, port:{}", ip, port);
            return true;
        } catch (Exception e) {
            logger.info("此ip不可用. ip:{}, port:{}", ip, port);
            return false;
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void test(WebDriver webDriver, String word) throws Exception {
        webDriver.get("http://www.cip.cc/");
        Thread.sleep(3000);
        String title = webDriver.getTitle();
        System.out.println(title);
        if (StringUtils.hasText(title)) {
            System.out.println(webDriver.findElement(By.tagName("pre")).getText());
        } else {
            genericObjectPool.invalidateObject(webDriver);
        }

    }

}
