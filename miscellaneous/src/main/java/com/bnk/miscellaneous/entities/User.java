package com.bnk.miscellaneous.entities;


import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;
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
//    @Column(name="firstname")
//    String firstname;
//    @Column(name="lastname")
//    String lastname;
    @Column(name="image_url")
    String imageUrl;
    @Column(name="password")
    String password;

    @OneToMany(mappedBy = "user")
    List<RecipientList> recipientLists;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "user")
    List<Task> taskList;

    String roles;

    public void addTaskToList(Task task) {
        if(taskList==null) {
            taskList = new ArrayList<>();
        }
        taskList.add(task);
    }

    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
