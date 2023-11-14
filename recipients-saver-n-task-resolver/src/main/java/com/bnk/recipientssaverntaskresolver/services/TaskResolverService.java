package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.dtos.TaskDto;
import com.bnk.recipientssaverntaskresolver.entities.User;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientList;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Notification;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import com.bnk.recipientssaverntaskresolver.repositories.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;
    UserRepository userRepository;
    @Transactional
    public void saveTaskAndCreateNotifications(TaskDto taskDto) {
        log.info("TaskResolverService saveTaskAndCreateNotifications taskDto: {} ", taskDto);
        Optional<User> optionalUser = userRepository.findById(taskDto.getUserId());
        if (optionalUser.isPresent()) {


            Task task = taskRepository.save(new Task(
                    taskDto.getRecipientsListName(),
                    taskDto.getText(),
                    optionalUser.get()
            ));

//            optionalUser.get().addTaskToList(task);


           Optional<RecipientList> recipientListOptional = recipientListNameRepository
                    .findByName(taskDto.getRecipientsListName(
                    ));
           if(recipientListOptional.isPresent()) {

               recipientListOptional
                       .get()
                       .getRecipientList()
                       .forEach(
                           (recipient) -> {
                               notificationRepository.save(
                                       new Notification(
                                               recipient,
                                               (short) 0,
                                               task)
                               );
                       }
               );



           } else {
               throw new IllegalArgumentException();
           }

        } else {
            throw new IllegalStateException();
        }
    }


}
