package com.bnk.recipientssaverntaskresolver.controllers;

import com.bnk.recipientssaverntaskresolver.dtos.requests.TaskRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.TaskResponseDto;
import com.bnk.recipientssaverntaskresolver.services.TaskResolverService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskResolverService taskResolverService;

    @PostMapping("/task/new")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto saveTaskAndCreateNotifications(@RequestBody TaskRequestDto taskRequestDto, Principal principal) {
        return taskResolverService.saveTaskAndCreateNotifications(taskRequestDto, principal.getName());
    }

    @GetMapping("/task/{id}/info")
    public TaskResponseDto getTaskInfoById(@PathVariable("id") Long id, Principal principal) {
        return taskResolverService.getTaskInfoById(id, principal.getName());
    }
}
