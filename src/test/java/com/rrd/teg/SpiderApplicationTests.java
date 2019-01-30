package com.rrd.teg;

import com.rrd.teg.optimus.schedule.ScheduledTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpiderApplicationTests {

    @Autowired
    private ScheduledTask scheduledTask;

    @Test
    public void test() {
        scheduledTask.spiderProxy();
    }
}
