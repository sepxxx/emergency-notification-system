package com.bnk.miscellaneous.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="notifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipient_id")
    Recipient recipient;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    Task task;

    @Column(name="created_at")
    Instant createdAt = Instant.now();


    @Column(name="next_retry_at")
    Instant nextRetryAt = createdAt;
    @Column(name="status")
    boolean status = false;

    public Notification(Recipient recipient, Task task) {
        this.recipient = recipient;
        this.task = task;
    }
}
