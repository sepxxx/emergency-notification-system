package com.bnk.miscellaneous.entities;


import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="tasks")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
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

    @Column(name="created_at")
    LocalDateTime createdAt = LocalDateTime.now();
////    Instant updatedAt;
//
//    @Column(name="next_retry_at")
//    Instant nextRetryTime = createdAt;
////    @Column(name="latest_try_at")
////    Instant latestTryAt;
//
//    boolean status = false;



    public Task(String recipientListName, String text, User user) {
        this.recipientListName = recipientListName;
        this.text = text;
        this.user = user;
    }
}
