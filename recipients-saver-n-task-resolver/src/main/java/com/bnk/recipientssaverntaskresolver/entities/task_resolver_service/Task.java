package com.bnk.recipientssaverntaskresolver.entities.task_resolver_service;


import com.bnk.recipientssaverntaskresolver.entities.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="tasks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @Column(name="recipient_list_name")
    String recipientListName;

    @Column(name="text")
    String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;
    public Task(String recipientListName, String text) {
        this.recipientListName = recipientListName;
        this.text = text;
    }
}
