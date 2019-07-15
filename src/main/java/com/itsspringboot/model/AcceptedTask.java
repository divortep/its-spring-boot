package com.itsspringboot.model;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accepted_tasks")
@Getter()
@EqualsAndHashCode(callSuper = true)
@ToString
public class AcceptedTask extends Task {

  private Date acceptedDate;
  private String teammate;

  protected AcceptedTask() {
    super();
  }

  public AcceptedTask(Task task, Date acceptedDate, String teammate) {
    super(task);
    this.acceptedDate = acceptedDate;
    this.teammate = teammate;
  }
}
