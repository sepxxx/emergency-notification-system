package com.bnk.notificationsender.services;

import com.bnk.miscellaneous.entities.Notification;
import com.bnk.miscellaneous.entities.Recipient;
import com.bnk.miscellaneous.repositories.NotificationRepository;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FetchAndExecuteTaskService {

    NotificationRepository notificationRepository;

    ///ВНИМАНИЕ
    //таска выполняется внутри открытой транзакции
    //используется пессимичтическая блокировка
    String title = "Attention";
    int priority = 2;
    String baseUrl = "https://alertzy.app/send";
    Random r = new Random();
    @SneakyThrows
    @Transactional
    @Scheduled(fixedDelay = 5000L)
//    @Scheduled(cron = "*/5 * * * * *")

    public void fetchAndSendNotification() {
//        Random r = new Random();
        log.info("FETCHING STARTED");
        Optional<Notification> notificationOptional = notificationRepository.findNextNotificationToSend();

        if(notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            try {
                Recipient recipient = notification.getRecipient();
//                Thread.sleep(10);//тут типа отправляем

//                if(r.nextInt(11) < 2) {
//                    log.info("Could deliver msg to: "+ recipient.getEmail());
//                    throw new RuntimeException("Could deliver msg to: " + recipient.getEmail());
//                }


                // Параметры запроса
                String accountKey = recipient.getToken();

                String message = notification.getTask().getText();
                log.info("message {}", message);
                String encodedMessage = URLEncoder.encode(message, "UTF-8");
                String encodedTitle = URLEncoder.encode(title, "UTF-8");
                // Построение URL с параметрами
                String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("accountKey", accountKey)
                        .queryParam("title", encodedTitle)
                        .queryParam("message", encodedMessage)
                        .queryParam("priority", priority)
                        .toUriString();

                // Выполнение GET-запроса
                ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);

                // Вывод результата
                System.out.println("Response Code: " + response.getStatusCodeValue());
                System.out.println("Response Body: " + response.getBody());
                notification.setStatus(true);
                log.info("notif sent to "+ recipient.getEmail());
            } catch (Exception e) {
                notification.setNextRetryAt(Instant.now().plusMillis(r.nextInt(500, 3000)));
            }

        }



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
