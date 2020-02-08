package com.itsspringboot.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

import com.itsspringboot.exception.AppException;
import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Performer;
import com.itsspringboot.model.Task;
import com.itsspringboot.model.User;
import com.itsspringboot.model.UserSettings;
import com.itsspringboot.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private TaskRepository taskRepository;
  private EmailNotificationService emailNotificationService;
  private UserService userService;

  @Autowired
  public TaskService(final TaskRepository taskRepository, final EmailNotificationService emailNotificationService,
                     final UserService userService) {
    this.taskRepository = taskRepository;
    this.emailNotificationService = emailNotificationService;
    this.userService = userService;
  }

  public List<Task> getAvailableTasks() {
    List<Task> tasks = taskRepository.getTasks();
    List<AcceptedTask> acceptedTasks = taskRepository.getAcceptedTasks();
    Set<String> acceptedTasksIds = acceptedTasks.stream().map(Task::getId).collect(Collectors.toSet());
    return tasks.stream().filter(task -> !acceptedTasksIds.contains(task.getId())).collect(Collectors.toList());
  }

  public List<AcceptedTask> getAcceptedByMeOrWithMeTasks() {
    return taskRepository.getAcceptedByMeOrWithMeTasks();
  }

  public AcceptedTask acceptTask(final String taskId, final boolean withTeammate) {
    if (isEmpty(taskId)) {
      throw new AppException("Task id can't be empty");
    }

    final User user = userService.getCurrentUser()
        .orElseThrow(() -> new AppException("Task can't be accepted anonymously."));

    final Performer acceptedBy = new Performer(user);
    final Performer teammate = Optional.ofNullable(user.getSettings())
        .map(UserSettings::getTeammate)
        .orElse(null);

    final Task task = taskRepository.getTask(taskId)
        .orElseThrow(() -> new AppException("Task can't be found with id: " + taskId));

    notifyIKEA(task.getNumber(), acceptedBy, withTeammate && teammate != null ? teammate : null);

    return taskRepository.acceptTask(task, acceptedBy, withTeammate ? teammate : null);
  }

  private void notifyIKEA(final String taskNumber, final Performer acceptedBy, final Performer teammate) {
    String message = "";
    if (teammate != null && isNoneEmpty(teammate.getName())) {
      message = String.format("Me (%s) and %s (%s) please.", acceptedBy.getEmail(), teammate.getName(), teammate.getEmail());

    } else {
      message = String.format("Me (%s) please.", acceptedBy.getEmail());
    }

    emailNotificationService.notifyIKEA(taskNumber, message);
  }

  public AcceptedTask markTaskDone(final String taskId) {
    if (isEmpty(taskId)) {
      throw new AppException("Task id can't be empty");
    }

    final AcceptedTask acceptedTask = taskRepository.getAcceptedTask(taskId)
        .orElseThrow(() -> new AppException("Accepted task can't be found with id: " + taskId));

    return taskRepository.markTaskDone(acceptedTask);
  }
}
