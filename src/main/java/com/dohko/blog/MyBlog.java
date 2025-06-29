package com.dohko.blog;

import com.dohko.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBlog implements CommandLineRunner {

    @Autowired
    private ArticleService as;

    public static void main(String[] args) {
        SpringApplication.run(MyBlog.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
