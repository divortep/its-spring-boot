package com.itsspringboot.schedule;

import static java.util.Collections.disjoint;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.collections4.ListUtils.subtract;

import com.itsspringboot.model.Task;
import com.itsspringboot.repository.TaskDocumentRepository;
import com.itsspringboot.repository.TaskRepository;
import com.itsspringboot.service.FCMNotificationService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskLoader {

  private TaskDocumentRepository taskDocumentRepository;
  private TaskRepository taskRepository;
  private FCMNotificationService fcmNotificationService;

  @Autowired
  public TaskLoader(TaskDocumentRepository taskDocumentRepository,
                    TaskRepository taskRepository) {
    this.taskDocumentRepository = taskDocumentRepository;
    this.taskRepository = taskRepository;
    this.fcmNotificationService = fcmNotificationService;
  }

  //@Scheduled(fixedRate = 30000)
  public void loadTasks() {
    List<Task> documentTasks = taskDocumentRepository.getTasks();
    List<Task> savedTasks = taskRepository.getTasks();

    List<Task> tasksToSave = unmodifiableList(subtract(documentTasks, savedTasks));
    List<Task> tasksToRemove = unmodifiableList(subtract(savedTasks, documentTasks));

    taskRepository.saveAll(tasksToSave);
    taskRepository.removeAll(tasksToRemove);

    final String tasksNumbers = tasksToSave.stream().map(Task::getNumber).collect(joining(", "));
    fcmNotificationService.notify("New Ikea tasks.", tasksNumbers);
  }
}
