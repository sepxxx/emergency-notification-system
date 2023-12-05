package com.bnk.recipientssaverntaskresolver.controllers;

import com.bnk.recipientssaverntaskresolver.dtos.requests.TaskRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.TaskResponseDto;
import com.bnk.recipientssaverntaskresolver.exceptions.UnauthorizedException;
import com.bnk.recipientssaverntaskresolver.services.TaskResolverService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/task")
public class TaskController {
    TaskResolverService taskResolverService;


    //Если инжектить principal на уровне метода сервиса,то ругается что мало параметров из котроллера
    //если инжектить в сам сервис то - не находит бин
    //здесь работает, но думаю не хорошо что просто передаю имя каждый раз
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto saveTaskAndCreateNotifications(@RequestBody TaskRequestDto taskRequestDto, Principal principal) {
        return taskResolverService.saveTaskAndCreateNotifications(taskRequestDto, principal.getName());
    }

    @GetMapping("/{id}/info")
    public TaskResponseDto getTaskInfoById(@PathVariable("id") Long id, Principal principal) {
        return taskResolverService.getTaskInfoById(id, principal.getName());
    }

    @GetMapping("/all")
    public List<TaskResponseDto> getTasksList(Principal principal) {
        return taskResolverService.getTaskList(principal.getName());
    }
 }
