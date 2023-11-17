package com.bnk.notificationsender.repositories;


import com.bnk.store.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {
}
