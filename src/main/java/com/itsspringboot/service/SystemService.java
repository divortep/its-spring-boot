package com.itsspringboot.service;

import com.itsspringboot.model.User;
import java.io.IOException;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

  public static final String PLAY_MARKET_URL = "https://play.google.com/store/apps/details?id=its.ionic.app";

  public String getLatestClientVersion() throws IOException {
    return Jsoup.connect(PLAY_MARKET_URL)
        .timeout(30000)
        .get()
        .select(".hAyfc > .htlgb")
        .get(3)
        .text();
  }
}
