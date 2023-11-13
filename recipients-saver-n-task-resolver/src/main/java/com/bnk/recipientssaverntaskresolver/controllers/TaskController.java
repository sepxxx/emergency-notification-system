package com.bnk.recipientssaverntaskresolver.controllers;


import com.bnk.recipientssaverntaskresolver.dtos.TaskDto;
import com.bnk.recipientssaverntaskresolver.services.TaskResolverService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskResolverService taskResolverService;

    @PostMapping("/task/new")
    public void saveTaskAndCreateNotifications(@RequestBody TaskDto taskDto) {
        taskResolverService.saveTaskAndCreateNotifications(taskDto);
    }
}
