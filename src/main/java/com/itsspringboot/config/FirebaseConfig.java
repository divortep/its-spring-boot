package com.itsspringboot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  @Value("${its.fcm.notification.credentials}")
  private String fcmCredentials;

  @Value("${its.fcm.notification.database.url}")
  private String fcmDatabseUrl;

  @PostConstruct
  private void init() throws IOException {
    if (CollectionUtils.isEmpty(FirebaseApp.getApps())) {
      InputStream serviceAccount = IOUtils.toInputStream(fcmCredentials, "UTF-8");
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl(fcmDatabseUrl)
          .build();

      FirebaseApp.initializeApp(options);
    }
  }
}
