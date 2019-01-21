package cc.shik;

import cc.shik.job.SimpleDemoJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShikElasticJobApplicationTests {

	@Autowired
	private SimpleDemoJob simpleDemoJob;

	@Test
	public void contextLoads() {
		this.simpleDemoJob.execute(null);
	}

}
