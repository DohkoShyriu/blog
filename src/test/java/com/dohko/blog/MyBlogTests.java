package com.dohko.blog;

import com.dohko.blog.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MyBlogTests {

	@Autowired
	private ArticleService as;

	@Test
	void contextLoads() {
	}

}
