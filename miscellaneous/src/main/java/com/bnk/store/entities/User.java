package com.bnk.store.entities;


import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientList;
import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "user")
    List<RecipientList> recipientLists;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
@OneToMany(mappedBy = "user")
    List<Task> taskList;

    public void addTaskToList(Task task) {
        if(taskList==null) {
            taskList = new ArrayList<>();
        }
        taskList.add(task);
    }
 }
