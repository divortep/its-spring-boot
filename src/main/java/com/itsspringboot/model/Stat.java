package com.itsspringboot.model;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stats")
@Getter()
@ToString
@EqualsAndHashCode
public class Stat {

  @CreatedDate
  protected Date createdDate;

  @Id
  protected String id;

  protected Integer documentTasksCount;
  protected Integer newTasksCount;
  protected Integer removedTasksCount;

  public Stat(final Integer documentTasksCount,
              final Integer newTasksCount,
              final Integer removedTasksCount) {
    this.documentTasksCount = documentTasksCount;
    this.newTasksCount = newTasksCount;
    this.removedTasksCount = removedTasksCount;
  }
}
