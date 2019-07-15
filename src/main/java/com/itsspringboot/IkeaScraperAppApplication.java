package com.itsspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.itsspringboot")
public class IkeaScraperAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(IkeaScraperAppApplication.class, args);
  }

}
