package cc.shik.config;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project shik-elastic-job
 * @package cc.shik.config
 * @since 2019-01-18
 */
@Configuration
public class SeleniumConfig {

    // /opt/project/chromedriver
    @Bean
    public WebDriver selenium() {
//        Proxy proxy = new Proxy();
//        String proxyIpAndPort = "http://localhost:1080";
//        System.out.println(proxyIpAndPort);
//        proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
//        DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
//        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
//        cap.setCapability(CapabilityType.PROXY, proxy);
        ChromeOptions chromeOptions = new ChromeOptions(); //.merge(cap);
        chromeOptions.addArguments("--headless","--no-sandbox","--disable-extensions","--disable-gpu");
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        return webDriver;
    }

}
