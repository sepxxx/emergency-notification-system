package com.bnk.store.entities;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

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

    @Column(name="status")
    short status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    Task task;

    public Notification(Recipient recipient, short status, Task task) {
        this.recipient = recipient;
        this.status = status;
        this.task = task;
    }
}
