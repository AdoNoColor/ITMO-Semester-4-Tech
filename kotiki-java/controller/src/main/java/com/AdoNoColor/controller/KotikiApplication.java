package com.AdoNoColor.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import java.util.logging.Filter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.AdoNoColor.*"})
@EntityScan(basePackages = {"com.AdoNoColor.*"})
@EnableJpaRepositories(basePackages = {"com.AdoNoColor.*"})
public class KotikiApplication {
    public static void main(String[] args) {
        SpringApplication.run(KotikiApplication.class);
    }
    @Bean
    public SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter() {
        return new SecurityContextHolderAwareRequestFilter();
    }
}


