package com.projects7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProjectS7Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ProjectS7Application.class, args);
        System.out.println("************************");
    }

}
