package com.rrd.teg.optimus.config.pool;

import com.rrd.teg.optimus.runner.Init;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class WebDriverPoolConfig {

    @Bean("genericObjectPool")
    public GenericObjectPool<WebDriver> genericObjectPool(
            @Qualifier("pooledWebDriverFactory") PooledWebDriverFactory pooledWebDriverFactory,
            @Qualifier("genericObjectPoolConfig") GenericObjectPoolConfig genericObjectPoolConfig) {
        GenericObjectPool<WebDriver> genericObjectPool = new GenericObjectPool<WebDriver>(pooledWebDriverFactory, genericObjectPoolConfig);
        return genericObjectPool;

    }

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        Init.initProxy();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(8);// 整个池最大值
        config.setMaxIdle(8);// 最大空闲
        config.setMinIdle(0);// 最小空闲
        config.setMaxWaitMillis(2000);// 最大等待时间，-1表示一直等
        config.setBlockWhenExhausted(true);// 当对象池没有空闲对象时，新的获取对象的请求是否阻塞。true阻塞。默认值是true
        config.setTestOnBorrow(false);// 在从对象池获取对象时是否检测对象有效，true是；默认值是false
        config.setTestOnReturn(false);// 在向对象池中归还对象时是否检测对象有效，true是，默认值是false
        config.setTestWhileIdle(false);// 在检测空闲对象线程检测到对象不需要移除时，是否检测对象的有效性。true是，默认值是false
        config.setMinEvictableIdleTimeMillis(60 * 60000L); // 可发呆的时间,10mins
        config.setTestWhileIdle(false); // 发呆过长移除的时候是否test一下先
        config.setJmxEnabled(false);
        return config;
    }

    @Bean
    public PooledWebDriverFactory pooledWebDriverFactory() {
        return new PooledWebDriverFactory();
    }
}
