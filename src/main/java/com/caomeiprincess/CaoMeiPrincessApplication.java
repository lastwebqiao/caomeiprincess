package com.caomeiprincess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.caomeiprincess.mapper")
public class CaoMeiPrincessApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CaoMeiPrincessApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CaoMeiPrincessApplication.class, args);
    }

}
