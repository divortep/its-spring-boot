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
  private String email;

  public Performer() {
  }

  @PersistenceConstructor
  public Performer(final String id, final String name, final String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Performer(final User user) {
    if (user != null) {
      this.id = user.getId();
      this.name = user.getName();
      this.email = user.getPersonalEmail();
    }
  }

  public Performer(final Performer performer) {
    if (performer != null) {
      this.id = performer.getId();
      this.name = performer.getName();
      this.email = performer.getEmail();
    }
  }
}
