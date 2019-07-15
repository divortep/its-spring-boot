package com.itsspringboot.repository.impl;

import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Task;
import com.itsspringboot.repository.TaskRepository;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private MongoTemplate mongoTemplate;

  public TaskRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<Task> getTask(String taskId) {
    return Optional.ofNullable(mongoTemplate.findById(taskId, Task.class));
  }

  @Override
  public List<Task> getTasks() {
    return Collections.unmodifiableList(mongoTemplate.findAll(Task.class));
  }

  @Override
  public List<AcceptedTask> getAcceptedTasks() {
    return Collections.unmodifiableList(mongoTemplate.findAll(AcceptedTask.class));
  }

  @Override
  public AcceptedTask acceptTask(Task task, String teammate) {
    AcceptedTask acceptedTask = new AcceptedTask(task, new Date(), teammate);
    return mongoTemplate.save(acceptedTask);
  }

  @Override
  public Optional<Task> save(Task task) {
    return Optional.ofNullable(mongoTemplate.save(task));
  }

  @Override
  public List<Task> saveAll(List<Task> tasks) {
    tasks.stream()
        .map(Task::new)
        .forEach(mongoTemplate::save);
    return Collections.unmodifiableList(tasks);
  }

  @Override
  public void remove(Task task) {
    mongoTemplate.remove(task);
  }

  @Override
  public void removeAll(List<Task> tasks) {
    tasks.stream()
        .map(Task::new)
        .forEach(mongoTemplate::remove);
  }
}
