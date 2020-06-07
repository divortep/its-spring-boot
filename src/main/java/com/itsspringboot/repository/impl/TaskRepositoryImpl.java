package com.itsspringboot.repository.impl;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ImmutableList;
import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Performer;
import com.itsspringboot.model.Task;
import com.itsspringboot.model.User;
import com.itsspringboot.repository.TaskRepository;
import com.itsspringboot.service.UserService;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private MongoTemplate mongoTemplate;
  private UserService userService;

  @Autowired
  public void setMongoTemplate(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Autowired
  public void setUserService(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public Optional<Task> getTask(final String taskId) {
    return Optional.ofNullable(mongoTemplate.findById(taskId, Task.class));
  }

  @Override
  public Optional<AcceptedTask> getAcceptedTask(final String taskId) {
    return Optional.ofNullable(mongoTemplate.findById(taskId, AcceptedTask.class));
  }

  @Override
  public List<Task> getTasks() {
    return unmodifiableList(mongoTemplate.findAll(Task.class));
  }

  @Override
  public List<AcceptedTask> getAcceptedTasks() {
    return Collections.unmodifiableList(mongoTemplate.findAll(AcceptedTask.class));
  }

  @Override
  public List<AcceptedTask> getAcceptedTasks(final Date from, final Date to) {
    final Criteria fromCriteria = from != null ? Criteria.where("acceptedDate").gte(from) : null;
    final Criteria toCriteria = to != null ? Criteria.where("acceptedDate").lte(to) : null;

    Query query;
    if (fromCriteria != null && toCriteria != null) {
      query = new Query().addCriteria(new Criteria().orOperator(fromCriteria, toCriteria));

    } else if (fromCriteria != null) {
      query = new Query().addCriteria(fromCriteria);

    } else if (toCriteria != null) {
      query = new Query().addCriteria(toCriteria);

    } else {
      return emptyList();
    }

    return unmodifiableList(mongoTemplate.find(query, AcceptedTask.class));
  }

  @Override
  public List<AcceptedTask> getAcceptedByMeOrWithMeTasks() {
    final String currentUserId = userService.getCurrentUser()
        .map(User::getId)
        .orElse("");

    final Query query = new Query().addCriteria(
        new Criteria().orOperator(
            Criteria.where("acceptedBy.id").is(currentUserId),
            Criteria.where("teammate.id").is(currentUserId)
        )
    );
    return Collections.unmodifiableList(mongoTemplate.find(query, AcceptedTask.class));
  }

  @Override
  public AcceptedTask acceptTask(final Task task, final Performer acceptedBy, final Performer teammate) {
    AcceptedTask acceptedTask = new AcceptedTask(task, new Date(), acceptedBy, teammate, false);
    return mongoTemplate.save(acceptedTask);
  }

  @Override
  public AcceptedTask markTaskDone(final AcceptedTask acceptedTask) {
    return mongoTemplate.save(new AcceptedTask(acceptedTask, true));
  }

  @Override
  public <T extends Task> Optional<T> save(final T task) {
    return Optional.ofNullable(mongoTemplate.save(task));
  }

  @Override
  public <T extends Task> List<T> saveAll(final List<T> tasks) {
    return tasks.stream().map(mongoTemplate::save).collect(toImmutableList());
  }

  @Override
  public <T extends Task> void remove(final T task) {
    mongoTemplate.remove(task);
  }

  @Override
  public <T extends Task> void removeAll(final List<T> tasks) {
    tasks.stream().forEach(mongoTemplate::remove);
  }
}
