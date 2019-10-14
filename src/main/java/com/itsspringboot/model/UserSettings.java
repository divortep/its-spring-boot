package com.itsspringboot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter()
@EqualsAndHashCode
@ToString
public class UserSettings {

  private Performer teammate;


  public UserSettings() {
  }

  @PersistenceConstructor
  public UserSettings(final Performer teammate) {
    this.teammate = teammate;
  }

  public UserSettings(final UserSettings userSettings) {
    this.teammate = new Performer(userSettings.getTeammate());
  }
}
