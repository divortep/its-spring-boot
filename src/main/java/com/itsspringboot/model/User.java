package com.itsspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

  private List<Role> roles = ImmutableList.of();

  @JsonIgnore
  private String password;

  public User() {
  }

  public User(final String name, final String username, final String email,
              final String password, final ImmutableList<Role> roles) {
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public User(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.roles = user.roles;
  }
}
