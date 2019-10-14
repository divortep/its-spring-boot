package com.itsspringboot.repository;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Performer;
import com.itsspringboot.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  Optional<Task> getTask(String taskId);

  Optional<AcceptedTask> getAcceptedTask(String taskId);

  List<Task> getTasks();

  List<AcceptedTask> getAcceptedTasks();

  List<AcceptedTask> getAcceptedByMeOrWithMeTasks();

  AcceptedTask acceptTask(Task task, Performer acceptedBy, Performer teammate);

  AcceptedTask markTaskDone(AcceptedTask acceptedTask);

  Optional<Task> save(Task task);

  List<Task> saveAll(List<Task> tasks);

  void remove(Task task);

  void removeAll(List<Task> tasks);
}
