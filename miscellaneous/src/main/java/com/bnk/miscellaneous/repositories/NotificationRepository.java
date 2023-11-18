package com.bnk.miscellaneous.repositories;


import com.bnk.miscellaneous.entities.Notification;
import com.bnk.miscellaneous.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
        select * from notifications
        where status is false
        and next_retry_at <= NOW()
        limit 1 for update skip locked
    """, nativeQuery = true)
    Optional<Notification> findNextNotificationToSend();
}
