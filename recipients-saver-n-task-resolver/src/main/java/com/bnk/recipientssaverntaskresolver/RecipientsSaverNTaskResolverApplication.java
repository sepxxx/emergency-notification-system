package com.bnk.recipientssaverntaskresolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RecipientsSaverNTaskResolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipientsSaverNTaskResolverApplication.class, args);
    }

}
