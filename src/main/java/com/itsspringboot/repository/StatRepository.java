package com.itsspringboot.repository;

import com.itsspringboot.model.Stat;
import java.util.Optional;

public interface StatRepository {

  Optional<Stat> save(Stat stat);

  void removeAll();
}
