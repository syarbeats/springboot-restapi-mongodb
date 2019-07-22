package com.mitrais.cdc.mongodbapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MongodbAppApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
        return app.sources(MongodbAppApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MongodbAppApplication.class, args);
    }

}
