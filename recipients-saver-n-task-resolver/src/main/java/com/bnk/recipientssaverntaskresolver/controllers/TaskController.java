package com.bnk.recipientssaverntaskresolver.controllers;


import com.bnk.recipientssaverntaskresolver.dtos.TaskDto;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import com.bnk.recipientssaverntaskresolver.repositories.TaskRepository;
import com.bnk.recipientssaverntaskresolver.services.TaskResolverService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskResolverService taskResolverService;

    TaskRepository taskRepository;

    @PostMapping("/task/new")
    public void saveTaskAndCreateNotifications(@RequestBody TaskDto taskDto) {
        taskResolverService.saveTaskAndCreateNotifications(taskDto);
    }

    @GetMapping("/task/{id}/info")
    public void getTaskInfoById(@PathVariable("id") Long id) {
        Task task = taskRepository.getById(id);

//        System.out.println(task.getId());
        System.out.println(task.getUser().getUsername());
    }
}
