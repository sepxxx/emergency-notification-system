package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.dtos.TaskDto;
import com.bnk.recipientssaverntaskresolver.entities.User;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientListName;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Notification;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import com.bnk.recipientssaverntaskresolver.repositories.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskResolverService {
    TaskRepository taskRepository;
    NotificationRepository notificationRepository;
//    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;
    UserRepository userRepository;
    @Transactional
    public void saveTaskAndCreateNotifications(TaskDto taskDto) {
        Task task = taskRepository.save(new Task(
                taskDto.getRecipientsListName(),
                taskDto.getText()
        ));
        List<Recipient> recipientList = recipientListNameRepository
                .findAllByListName(task.getRecipientListName());
        recipientList.forEach(
                (recipient)->{
                    notificationRepository.save(
                            new Notification(
                                recipient,
                                    (short) 0,
                                task)
                    );
                }
        );

        Optional<User> optionalUser = userRepository.findById(taskDto.getUserId());
        if(optionalUser.isPresent()) {
            optionalUser.get().addTaskToList(task);
        } else {
            throw new IllegalStateException();
        }
    }


}
