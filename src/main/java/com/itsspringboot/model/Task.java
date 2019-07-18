package com.itsspringboot.model;

import com.google.common.collect.ImmutableList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Getter()
@ToString
public class Task {

  @CreatedDate
  public Date createdDate;

  @Id
  private String id;

  private String number;
  private String date;
  private String timeSlot;
  private String address;
  private String quotedTime;
  private boolean hasPAX;
  private List<String> items = ImmutableList.of();

  protected Task() {
  }

  public Task(String number,
              String date,
              String timeSlot,
              String address,
              String quotedTime,
              ImmutableList<String> items) {
    this.number = number;
    this.date = date;
    this.timeSlot = timeSlot;
    this.address = address;
    this.quotedTime = quotedTime;
    this.items = items;
    this.hasPAX = hasPax(items);
  }

  public Task(Task task) {
    this.id = task.getId();
    this.createdDate = task.getCreatedDate();
    this.number = task.getNumber();
    this.date = task.getDate();
    this.timeSlot = task.getTimeSlot();
    this.address = task.getAddress();
    this.quotedTime = task.getQuotedTime();
    this.items = task.getItems();
    this.hasPAX = task.hasPAX;
  }

  private boolean hasPax(List<String> items) {
    return items.stream().anyMatch(item -> item.contains("PAX"));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Task task = (Task) o;
    return Objects.equals(number, task.number) &&
        Objects.equals(date, task.date) &&
        Objects.equals(timeSlot, task.timeSlot) &&
        Objects.equals(address, task.address) &&
        Objects.equals(quotedTime, task.quotedTime) &&
        Objects.equals(items, task.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, date, timeSlot, address, quotedTime, items);
  }
}
