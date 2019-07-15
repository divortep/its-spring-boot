package com.itsspringboot.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.itsspringboot.exception.AppException;
import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Task;
import com.itsspringboot.repository.TaskRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private TaskRepository taskRepository;

  @Autowired
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getAvailableTasks() {
    List<Task> tasks = taskRepository.getTasks();
    List<AcceptedTask> acceptedTasks = taskRepository.getAcceptedTasks();
    Set<String> acceptedTasksIds = acceptedTasks.stream().map(Task::getId).collect(Collectors.toSet());
    return tasks.stream().filter(task -> !acceptedTasksIds.contains(task.getId())).collect(Collectors.toList());
  }

  public List<AcceptedTask> getAcceptedTasks() {
    return taskRepository.getAcceptedTasks();
  }

  public AcceptedTask acceptTask(String taskId, String teammate) {
    if (isEmpty(taskId)) {
      throw new AppException("Task id can't be empty");
    }

    Task task = taskRepository.getTask(taskId)
        .orElseThrow(() -> new AppException("Task not found with id: " + taskId));

    return taskRepository.acceptTask(task, teammate);
  }
}
