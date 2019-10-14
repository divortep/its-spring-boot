package com.itsspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter()
@EqualsAndHashCode
@ToString
public class User {

  @Id
  private String id;

  private String name;
  private String username;
  private String email;
  private UserSettings settings;
  private List<Role> roles;

  @JsonIgnore
  private String password;

  @PersistenceConstructor
  public User(final String id, final String name, final String username, final String email,
              final String password, final List<Role> roles, final UserSettings settings) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.roles = ImmutableList.copyOf(roles);
    this.settings = new UserSettings(settings);
  }

  public User(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.roles = ImmutableList.copyOf(user.roles);
    this.settings = new UserSettings(user.getSettings());
  }
}
