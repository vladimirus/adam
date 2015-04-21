package com.adam;

import com.adam.manager.scenario.PostAThought;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdamApplication.class)
public class AdamApplicationTests {

	@Autowired
	private PostAThought thought;


	@Test
	public void contextLoads() {
	}

	@Test
	public void postThought() {
		thought.postAThought();
	}

}
