package com.itsspringboot.repository;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Performer;
import com.itsspringboot.model.Task;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  Optional<Task> getTask(String taskId);

  Optional<AcceptedTask> getAcceptedTask(String taskId);

  List<Task> getTasks();

  List<AcceptedTask> getAcceptedTasks();

  List<AcceptedTask> getAcceptedTasks(Date from, Date to);

  List<AcceptedTask> getAcceptedByMeOrWithMeTasks();

  AcceptedTask acceptTask(Task task, Performer acceptedBy, Performer teammate);

  AcceptedTask markTaskDone(AcceptedTask acceptedTask);

  <T extends Task> Optional<T> save(T task);

  <T extends Task> List<T> saveAll(List<T> tasks);

  <T extends Task> void remove(T task);

  <T extends Task> void removeAll(List<T> tasks);
}
