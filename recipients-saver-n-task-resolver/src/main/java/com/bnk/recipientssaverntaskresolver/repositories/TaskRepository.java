package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.task_resolver_service.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
