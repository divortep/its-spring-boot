package com.itsspringboot.repository;

import com.itsspringboot.model.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(String id);

  Optional<User> findByUsernameOrEmail(String usernameOrEmail);

  Optional<User> saveUser(User user);
}
