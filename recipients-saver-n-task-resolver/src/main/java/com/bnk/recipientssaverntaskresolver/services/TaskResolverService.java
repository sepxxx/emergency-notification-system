package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.miscellaneous.entities.Notification;
import com.bnk.miscellaneous.entities.RecipientList;
import com.bnk.miscellaneous.entities.Task;
import com.bnk.miscellaneous.entities.User;
import com.bnk.miscellaneous.repositories.NotificationRepository;

import com.bnk.recipientssaverntaskresolver.dtos.requests.TaskRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.TaskResponseDto;
import com.bnk.recipientssaverntaskresolver.exceptions.ForbiddenException;
import com.bnk.recipientssaverntaskresolver.exceptions.NotFoundException;
import com.bnk.recipientssaverntaskresolver.exceptions.UnauthorizedException;
import com.bnk.recipientssaverntaskresolver.repositories.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TaskResolverService {
    TaskRepository taskRepository;
    NotificationRepository notificationRepository;
    RecipientListNameRepository recipientListNameRepository;
    UserRepository userRepository;


    public TaskResponseDto getTaskInfoById(Long id, String currentUsername){
        log.info("getTaskInfoById id: {}, currentUsername: {} ",id, currentUsername);

        User user = userRepository.findByUsername(currentUsername)
                        .orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task by id "+id+ " not found"));
        log.info("getTaskInfoById task: {} ", task);
        log.info("getTaskInfoById user.getTaskList(): {} ", user.getTaskList());

        //TODO а что если тут сделать метод который работает только с id, как повысится производительность?
        if(user.getTaskList().contains(task)) {
            log.info("getTaskInfoById у юзера: {} есть таска: {} ", user.getId(), task.getId());
            return new TaskResponseDto(task.getId(), task.getRecipientListName(),
                    task.getText(),task.getCreatedAt(), 10L, 10L);
        } else
//            throw new UnauthorizedException();
            throw new ForbiddenException("user: "+ currentUsername +"doesnt have access to task with id:"+ id);

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
                .findByNameAndUser(recipientListName, user).orElseThrow(() -> new NotFoundException("recipientList with name: "+recipientListName+ " not found"));

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

        return new TaskResponseDto(task.getId(), task.getRecipientListName(), task.getText(),
                LocalDateTime.now() ,10L,10L);
    }

    //тут вот тоже вроде не ок
    //внутри у меня сет а отдаю наружу лист

    public List<TaskResponseDto> getTaskList(String currentUsername) {
        log.info(" getTaskList currentUsername: {} ", currentUsername);
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user.getTaskList().stream().map(task-> {
            return new TaskResponseDto(task.getId(), task.getRecipientListName(), task.getText(), task.getCreatedAt(),
                   10L,10L);
        }).collect(Collectors.toList());
    }
}
