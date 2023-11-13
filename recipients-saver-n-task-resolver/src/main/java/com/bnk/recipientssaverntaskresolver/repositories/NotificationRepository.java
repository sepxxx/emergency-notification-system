package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {
}
