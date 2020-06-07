package com.itsspringboot.model;

import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "archived_tasks")
@Getter()
@ToString
public class ArchivedTask extends AcceptedTask {

  @CreatedDate
  protected Date archivedDate;

  public ArchivedTask(final AcceptedTask task) {
    super(task, true);
  }
}
