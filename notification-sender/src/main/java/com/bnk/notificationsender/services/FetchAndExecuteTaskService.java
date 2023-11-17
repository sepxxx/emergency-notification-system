package com.bnk.notificationsender.services;

public class FetchAndExecuteTaskService {
//    TaskRepository taskRepository;
//
//    ///ВНИМАНИЕ
//    //таска выполняется внутри открытой транзакции
//    //используется пессимичтическая блокировка
//
//    @Transactional
//    public void fetchAndExecuteTask() {
//        Task task = taskRepository.findNextTask(LocalDateTime.now());
//
//        try {
//            handle(task);
//            task.status = SUCCESS;
//        } catch(e: Exception) {
//            task.status = ERROR;
//        }
//
//        taskRepository.update(task);
//    }

//    """
//        select * from tasks
//        where status is NULL
//        and next_retry_time <= '2022-04-28 ...'
//        limit 1 for update skip locked
//    """
    //skip locked позволяет пропускать занятые строчки
    //не ждем пессиместичную блокировку
    //если все будут заняты - будует пустой ответ


//    if(task.retryCounter < MAX_RETRIES) {
//        task.retryCounter++;
//        task.nextRetryTime = LocalDateTime.now() + randomDistribution(retryCounter);
//
//    } else {
//        task.status = ERROR;
//    }

//    public static void main(String[] args) {
//        System.out.println(Instant.now());
//        System.out.println(Duration.ofSeconds(5).toMillis());
//        System.out.println(System.currentTimeMillis());
//    }
}
