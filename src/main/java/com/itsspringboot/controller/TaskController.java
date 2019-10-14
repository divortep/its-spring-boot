package com.itsspringboot.controller;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Task;
import com.itsspringboot.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskController {

  private TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
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
}
