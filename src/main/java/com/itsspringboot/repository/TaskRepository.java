package com.itsspringboot.repository;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  Optional<Task> getTask(String taskId);

  List<Task> getTasks();

  List<AcceptedTask> getAcceptedTasks();

  AcceptedTask acceptTask(Task task, boolean withTeammate);

  Optional<Task> save(Task task);

  List<Task> saveAll(List<Task> tasks);

  void remove(Task task);

  void removeAll(List<Task> tasks);
}
