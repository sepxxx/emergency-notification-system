package com.bnk.miscellaneous.repositories;


import com.bnk.miscellaneous.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {
}
