package com.rrd.teg.schedule;

import com.rrd.teg.job.SimpleDemoJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private SimpleDemoJob simpleDemoJob;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void test1() {
        this.simpleDemoJob.execute(null);
    }

}
