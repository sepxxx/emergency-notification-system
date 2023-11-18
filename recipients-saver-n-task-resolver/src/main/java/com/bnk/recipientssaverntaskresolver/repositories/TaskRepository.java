package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.miscellaneous.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
        select task
        from Task task
        where task.status is false
        and task.next_retry_at <= NOW()
        limit 1 for update skip locked
    """, nativeQuery = true)
    Optional<Task> findNextFreeTask();
}
