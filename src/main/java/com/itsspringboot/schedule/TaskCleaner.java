package com.itsspringboot.schedule;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.ArchivedTask;
import com.itsspringboot.repository.TaskRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskCleaner {

  private TaskRepository taskRepository;

  @Autowired
  public void setTaskRepository(final TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Scheduled(cron = "0 0 0 * * MON")
  public void markAcceptedTasksDone() {
    Date to = Date.from(LocalDate.now()
        .minusWeeks(2)
        .atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant());

    List<AcceptedTask> tasksForPreviousWeek = taskRepository.getAcceptedTasks(null, to);
    tasksForPreviousWeek.stream()
        .map(ArchivedTask::new)
        .forEach(task -> taskRepository.save(task));

    tasksForPreviousWeek.forEach(task -> taskRepository.remove(task));
  }
}
