package com.rrd.teg.optimus.config.pool;

import com.google.common.collect.Queues;
import com.rrd.teg.optimus.runner.Init;
import com.rrd.teg.optimus.schedule.ScheduledTask;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Queue;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project spider
 * @package com.rrd.teg.config.pool
 * @since 2019-01-22
 */
public class PooledWebDriverFactory extends BasePooledObjectFactory<WebDriver> {

    private static final Logger logger = LoggerFactory.getLogger(PooledWebDriverFactory.class);

    public static Queue<String> queue = Queues.newConcurrentLinkedQueue();

    @Override
    public WebDriver create() throws Exception {
        return this.init();
    }

    @Override
    public PooledObject<WebDriver> wrap(WebDriver webDriver) {
        return new DefaultPooledObject<>(webDriver);
    }

    public WebDriver init() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-extensions", "--disable-gpu");

        if (queue.isEmpty()) {
            logger.warn(">>>> queue.isEmpty >>>");
            Init.initProxy();
        }

        // 递归获取 代理ip 若为空，则不使用proxy
        String proxyIpAndPort = this.getProxy();
        if(StringUtils.hasText(proxyIpAndPort)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
            chromeOptions.setProxy(proxy);
        }
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        return webDriver;
    }

    private String getProxy() {
        String proxyIpAndPort = queue.poll();
        if(StringUtils.hasText(proxyIpAndPort)) {
            if(this.validateProxy(proxyIpAndPort)) {
                queue.add(proxyIpAndPort);
                return proxyIpAndPort;
            } else {
                logger.warn(">>>>>>> webDriver init时，代理ip 不可用, ip:{}", proxyIpAndPort);
                this.getProxy();
            }
        }
        logger.warn(">>>>>>> proxy queue 为空");
        return null;
    }

    private boolean validateProxy(String proxyIpAndPort) {

        URI uri = URI.create(proxyIpAndPort);

        uri.getHost(); uri.getPort();

        return ScheduledTask.verifyProxyIP(uri.getHost(), uri.getPort());

    }

}
