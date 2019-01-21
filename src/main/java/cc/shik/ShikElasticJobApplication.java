package cc.shik;

import cc.shik.job.SimpleDemoJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShikElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShikElasticJobApplication.class);
    }
}
