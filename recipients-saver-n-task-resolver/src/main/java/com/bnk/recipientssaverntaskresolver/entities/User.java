package com.bnk.recipientssaverntaskresolver.entities;


import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientListName;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(name="username")
    String username;

    @OneToMany
    List<RecipientListName> recipientListNames;

    @OneToMany
    List<Task> taskList;

    public void addTaskToList(Task task) {
        if(taskList==null) {
            taskList = new ArrayList<>();
        }
        taskList.add(task);
    }
 }
