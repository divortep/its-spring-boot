package com.itsspringboot.repository.impl;

import com.itsspringboot.model.Stat;
import com.itsspringboot.repository.StatRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatRepositoryImpl implements StatRepository {

  private MongoTemplate mongoTemplate;

  @Autowired
  public void setMongoTemplate(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<Stat> save(final Stat stat) {
    return Optional.ofNullable(mongoTemplate.save(stat));
  }

  @Override
  public void removeAll() {
    mongoTemplate.dropCollection(Stat.class);
  }
}
