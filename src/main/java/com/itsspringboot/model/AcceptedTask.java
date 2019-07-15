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

  private Date acceptedDate;
  private boolean withTeammate;

  protected AcceptedTask() {
    super();
  }

  public AcceptedTask(Task task, Date acceptedDate, boolean withTeammate) {
    super(task);
    this.acceptedDate = acceptedDate;
    this.withTeammate = withTeammate;
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
        Objects.equals(withTeammate, that.withTeammate);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), acceptedDate, withTeammate);
  }
}
