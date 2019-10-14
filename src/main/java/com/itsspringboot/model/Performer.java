package com.itsspringboot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter
@EqualsAndHashCode
@ToString
public class Performer {

  @Id
  private String id;
  private String name;

  public Performer() {
  }

  @PersistenceConstructor
  public Performer(final String id, final String name) {
    this.id = id;
    this.name = name;
  }

  public Performer(final User user) {
    if (user != null) {
      this.id = user.getId();
      this.name = user.getName();
    }
  }

  public Performer(final Performer performer) {
    if (performer != null) {
      this.id = performer.getId();
      this.name = performer.getName();
    }
  }
}
