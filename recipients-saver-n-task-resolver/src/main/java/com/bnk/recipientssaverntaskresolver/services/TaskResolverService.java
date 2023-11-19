package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.miscellaneous.entities.Notification;
import com.bnk.miscellaneous.entities.RecipientList;
import com.bnk.miscellaneous.entities.Task;
import com.bnk.miscellaneous.entities.User;
import com.bnk.miscellaneous.repositories.NotificationRepository;

import com.bnk.recipientssaverntaskresolver.dtos.TaskRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.TaskResponseDto;
import com.bnk.recipientssaverntaskresolver.exceptions.NotFoundException;
import com.bnk.recipientssaverntaskresolver.exceptions.UnauthorizedException;
import com.bnk.recipientssaverntaskresolver.repositories.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TaskResolverService {
    TaskRepository taskRepository;
    NotificationRepository notificationRepository;
    RecipientListNameRepository recipientListNameRepository;
    UserRepository userRepository;


    public TaskResponseDto getTaskInfoById(Long id, String currentUsername) {
        log.info("getTaskInfoById id: {}, currentUsername: {} ",id, currentUsername);

        User user = userRepository.findByUsername(currentUsername)
                        .orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task by id "+id+ " not found"));
        log.info("getTaskInfoById task: {} ", task);
        log.info("getTaskInfoById user.getTaskList(): {} ", user.getTaskList());
        if(user.getTaskList().contains(task)) {
            return new TaskResponseDto(task.getId(), task.getRecipientListName(), task.getText(), 10L, 10L);
        }
        else
            throw new UnauthorizedException("user: "+ currentUsername +"doesnt have access to task with id:"+ id);

    }


    @Transactional
    public TaskResponseDto saveTaskAndCreateNotifications(TaskRequestDto taskRequestDto, String currentUsername) {
        log.info(" saveTaskAndCreateNotifications taskRequestDto: {} ", taskRequestDto);
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Task task = taskRepository.save(new Task(
                taskRequestDto.getRecipientsListName(),
                taskRequestDto.getText(),
                user
        ));

        String recipientListName = taskRequestDto.getRecipientsListName();
        RecipientList recipientList = recipientListNameRepository
                .findByName(recipientListName).orElseThrow(() -> new NotFoundException("recipientList with name: "+recipientListName+ " not found"));

       //нормально ли делать много save здесь?
        recipientList
               .getRecipientList()
               .forEach(
                   (recipient) -> {
                       notificationRepository.save(
                               new Notification(
                                       recipient,
                                       task)
                       );
               }
       );

        return new TaskResponseDto(task.getId(), task.getRecipientListName(), task.getText(),10L,10L);
    }

}
