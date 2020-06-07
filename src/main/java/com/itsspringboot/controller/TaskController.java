package com.itsspringboot.controller;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Task;
import com.itsspringboot.schedule.TaskCleaner;
import com.itsspringboot.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class TaskController {

  private TaskService taskService;
  private TaskCleaner taskCleaner;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @Autowired
  public void setTaskCleaner(final TaskCleaner taskCleaner) {
    this.taskCleaner = taskCleaner;
  }

  @GetMapping("/availableTasks")
  @ResponseBody
  public List<Task> getAvailableTasks() {
    return taskService.getAvailableTasks();
  }

  @GetMapping("/acceptedTasks")
  @ResponseBody
  public List<AcceptedTask> acceptTask() {
    return taskService.getAcceptedByMeOrWithMeTasks();
  }

  @GetMapping("/task/{taskId}/accept")
  @ResponseBody
  public AcceptedTask acceptTask(@PathVariable String taskId,
                                 @RequestParam(required = false) boolean withTeammate) {
    return taskService.acceptTask(taskId, withTeammate);
  }

  @GetMapping("/task/{taskId}/done")
  @ResponseBody
  public AcceptedTask acceptTask(@PathVariable String taskId) {
    return taskService.markTaskDone(taskId);
  }

  @GetMapping("/archiveTasks")
  @ResponseStatus(HttpStatus.OK)
  public void archiveTasks() {
    taskCleaner.markAcceptedTasksDone();
  }
}
