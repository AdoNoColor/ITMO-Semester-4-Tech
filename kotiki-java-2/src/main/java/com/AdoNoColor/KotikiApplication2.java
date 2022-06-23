package com.AdoNoColor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"com.AdoNoColor.*"})
@EntityScan(basePackages = {"com.AdoNoColor.*"})
@EnableJpaRepositories(basePackages = {"com.AdoNoColor.*"})
public class KotikiApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(KotikiApplication2.class);
    }

    @Bean
    public JsonDeserializer jsonDeserializer() {
        return new JsonDeserializer();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


