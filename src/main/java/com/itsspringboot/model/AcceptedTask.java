package com.itsspringboot.model;

import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accepted_tasks")
@Getter()
@ToString
public class AcceptedTask extends Task {

  protected Date acceptedDate;
  protected Performer acceptedBy;
  protected Performer teammate;
  protected boolean isDone;

  protected AcceptedTask() {
    super();
  }

  public AcceptedTask(final Task task, final Date acceptedDate, final Performer acceptedBy,
                      final Performer teammate, final boolean isDone) {
    super(task);
    this.acceptedDate = acceptedDate;
    this.acceptedBy = acceptedBy;
    this.teammate = teammate;
    this.isDone = isDone;
  }

  public AcceptedTask(final AcceptedTask task, final boolean isDone) {
    super(task);
    this.acceptedDate = task.getAcceptedDate();
    this.acceptedBy = task.getAcceptedBy();
    this.teammate = task.getTeammate();
    this.isDone = isDone;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final AcceptedTask that = (AcceptedTask) o;
    return Objects.equals(acceptedDate, that.acceptedDate) &&
        Objects.equals(acceptedBy, that.acceptedBy) &&
        Objects.equals(teammate, that.teammate) &&
        Objects.equals(isDone, that.isDone);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), acceptedDate, acceptedBy, teammate, isDone);
  }
}
