package com.itsspringboot.schedule;

import com.itsspringboot.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatRemover {

  private StatRepository statRepository;

  @Autowired
  public void setStatRepository(final StatRepository statRepository) {
    this.statRepository = statRepository;
  }

  @Scheduled(cron = "0 0 0 ? * *")
  public void removeAllStat() {
    statRepository.removeAll();
  }
}
