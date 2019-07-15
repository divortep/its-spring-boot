package com.itsspringboot.repository.impl;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.itsspringboot.exception.BadRequestException;
import com.itsspringboot.model.User;
import com.itsspringboot.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private MongoTemplate mongoTemplate;

  @Autowired
  public UserRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<User> findById(final String userId) {
    return Optional.ofNullable(mongoTemplate.findById(userId, User.class));
  }

  @Override
  public Optional<User> findByUsernameOrEmail(final String usernameOrEmail) {
    Query query = new Query(new Criteria().
        orOperator(
            where("username").is(usernameOrEmail),
            where("email").is(usernameOrEmail)
        ));
    return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
  }

  @Override
  public Optional<User> saveUser(final User user) {
    Query query = new Query(new Criteria().
        orOperator(
            where("username").regex("^" + user.getUsername() + "$", "i"),
            where("email").regex("^" + user.getEmail() + "$", "i")
        ));
    User existingUser = mongoTemplate.findOne(query, User.class);
    if (existingUser != null && equalsIgnoreCase(existingUser.getUsername(), user.getUsername())) {
      throw new BadRequestException("Username is already taken!");

    } else if (existingUser != null && equalsIgnoreCase(existingUser.getEmail(), user.getEmail())) {
      throw new BadRequestException("Email Address already in use!");

    }

    return Optional.ofNullable(mongoTemplate.save(user));
  }
}
