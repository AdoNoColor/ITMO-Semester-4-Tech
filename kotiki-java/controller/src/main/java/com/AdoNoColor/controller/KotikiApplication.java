package com.AdoNoColor.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.AdoNoColor.*"})
@EntityScan(basePackages = {"com.AdoNoColor.*"})
@EnableJpaRepositories(basePackages = {"com.AdoNoColor.*"})
public class KotikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KotikiApplication.class);
    }
}
