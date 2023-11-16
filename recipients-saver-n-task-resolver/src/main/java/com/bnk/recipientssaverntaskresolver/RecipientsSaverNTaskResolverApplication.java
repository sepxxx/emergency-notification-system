package com.bnk.recipientssaverntaskresolver;

import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import com.bnk.recipientssaverntaskresolver.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
