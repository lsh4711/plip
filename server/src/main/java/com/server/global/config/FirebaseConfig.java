package com.server.global.config;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FirebaseConfig {
    @Value("${file.path}")
    private String basePath;

    @PostConstruct
    public void initFirebase() {
        String path = basePath + "/firebase.json";

        try {
            FileInputStream serviceAccount = new FileInputStream(path);
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            log.error("### Firebase Config Error: ", e.getMessage());
        }

    }
}
