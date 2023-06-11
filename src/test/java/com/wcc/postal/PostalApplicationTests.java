package com.wcc.postal;

import com.wcc.postal.controller.PostcodeController;
import com.wcc.postal.repository.PostcodeRepository;
import com.wcc.postal.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostalApplicationTests {

	@Autowired
	private PostcodeController postcodeController;

	@Autowired
	private PostcodeService postcodeService;

	@Autowired
	private PostcodeRepository postcodeRepository;

	@Test
	void contextLoads() {
		assertNotNull(postcodeController);
		assertNotNull(postcodeService);
		assertNotNull(postcodeRepository);
	}

}