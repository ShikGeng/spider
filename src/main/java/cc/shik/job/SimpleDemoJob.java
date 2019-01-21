package cc.shik.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.ByteVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project shik-elastic-job
 * @package cc.shik.job
 * @since 2019-01-14
 */
@Component
public class SimpleDemoJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(SimpleDemoJob.class);

    @Autowired
    private WebDriver webDriver;

    @Override
    public void execute(ShardingContext context) {
        webDriver.get("http://www.baidu.com");

        try {
            WebElement search_text = webDriver.findElement(By.xpath(""));
            WebElement search_button = webDriver.findElement(By.id("su"));

            search_text.sendKeys("培训");

            Thread.sleep(2000);
            WebElement element = webDriver.findElement(By.id("form")).findElement(By.tagName("ul"));
            System.out.println(element.getText());

            search_text.clear();
            Thread.sleep(2000);
            search_text.sendKeys("Selenium");
            Thread.sleep(2000);
            search_text.sendKeys("培训");
            search_button.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
