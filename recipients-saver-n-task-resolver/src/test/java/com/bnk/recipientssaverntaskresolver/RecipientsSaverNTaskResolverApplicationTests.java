package com.bnk.recipientssaverntaskresolver;

import com.bnk.recipientssaverntaskresolver.entities.User;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import com.bnk.recipientssaverntaskresolver.repositories.TaskRepository;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class RecipientsSaverNTaskResolverApplicationTests {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getTaskById() {
        Task task = taskRepository.getById(1L);
        task.getId();
//        System.out.println(task.getId());
    }

    @Test
    void findTaskById() {
        Optional<Task> taskOptional = taskRepository.findById(1L);
        taskOptional.get().getId();
//        System.out.println(task.getId());
    }

    @Test
    @Transactional
    void findUserById() {
//        Optional<User> userOptional = userRepository.findById(1L);
//        userOptional.get().getTaskList().get(0);
//        userRepository.findById(1L).get().getTaskList().get(0);
        userRepository.findById(1L).get();
//
    }



}
