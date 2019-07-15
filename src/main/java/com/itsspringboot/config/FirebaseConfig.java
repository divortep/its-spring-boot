package com.itsspringboot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  @Value("${its.fcm.notification.credentials.file}")
  private String fcmCredentialsFile;

  @Value("${its.fcm.notification.database.url}")
  private String fcmDatabseUrl;

  @PostConstruct
  private void init() throws IOException {
    if (CollectionUtils.isEmpty(FirebaseApp.getApps())) {
      ClassLoader classLoader = getClass().getClassLoader();
      File credentialsFile = new File(classLoader.getResource(fcmCredentialsFile).getFile());
      FileInputStream serviceAccount = new FileInputStream(credentialsFile);

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl(fcmDatabseUrl)
          .build();

      FirebaseApp.initializeApp(options);
    }
  }
}
