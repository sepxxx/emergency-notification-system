package com.bnk.notificationsender;

import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.clients.ApnsClientBuilder;
import com.clevertap.apns.exceptions.InvalidTrustManagerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Test {
    public static void main(String[] args) throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, InvalidTrustManagerException, KeyManagementException {
        FileInputStream cert = new FileInputStream("./certificate.p12");

        final ApnsClient client = new ApnsClientBuilder()
                .withProductionGateway()
                .inSynchronousMode()
                .withCertificate(cert)
                .withPassword("")
                .build();
//                .withDefaultTopic("")
        Notification n = new Notification
                .Builder("937bf03b56ed54ad27c99b17181c9dee94071dd249d5724df85e02251e19c121")
                .alertBody("Hello")
                .build();

        NotificationResponse response =  client.push(n);
        System.out.println(response);
    }

    }
