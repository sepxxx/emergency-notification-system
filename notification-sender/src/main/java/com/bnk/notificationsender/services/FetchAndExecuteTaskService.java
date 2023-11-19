package com.bnk.notificationsender.services;

import com.bnk.miscellaneous.entities.Recipient;
import com.bnk.miscellaneous.repositories.NotificationRepository;
import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.clients.ApnsClientBuilder;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FetchAndExecuteTaskService {
//    TaskRepository taskRepository;
    NotificationRepository notificationRepository;
//    private ResourceLoader resourceLoader;
    ApplicationContext applicationContext;
    ///ВНИМАНИЕ
    //таска выполняется внутри открытой транзакции
    //используется пессимичтическая блокировка

    @SneakyThrows
    @Transactional
    @Scheduled(fixedDelay = 5000L)
//    @Scheduled(cron = "*/5 * * * * *")
    public void fetchAndSendNotification() {
//        Random r = new Random();
////        log.info("FETCHING STARTED");
//        Optional<Notification> notificationOptional = notificationRepository.findNextNotificationToSend();
//
//        if(notificationOptional.isPresent()) {
//            Notification notification = notificationOptional.get();
//            try {
//                Recipient recipient = notification.getRecipient();
//                Thread.sleep(10);//тут типа отправляем
//
//                if(r.nextInt(11) < 2) {
//                    log.info("Could deliver msg to: "+ recipient.getEmail());
//                    throw new RuntimeException("Could deliver msg to: " + recipient.getEmail());
//                }
//                notification.setStatus(true);
//                log.info("notif sent to "+ recipient.getEmail());
//            } catch (Exception e) {
//                notification.setNextRetryAt(Instant.now().plusMillis(r.nextInt(500, 3000)));
//            }
//
//        }

        InputStream cert = applicationContext.getResource("classpath:certificate.p12").getInputStream();

        final ApnsClient client = new ApnsClientBuilder()
                .withProductionGateway()
                .inSynchronousMode()
                .withCertificate(cert)
                .withPassword("")
                .build();
//                .withDefaultTopic("")
        com.clevertap.apns.Notification n = new Notification
                .Builder("937bf03b56ed54ad27c99b17181c9dee94071dd249d5724df85e02251e19c121")
                .alertBody("Hello")
                .build();

        NotificationResponse response =  client.push(n);
        System.out.println(response);

    }

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
