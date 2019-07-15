package com.itsspringboot.mapper;

import com.google.common.collect.ImmutableList;
import com.itsspringboot.model.Task;
import com.mongodb.annotations.Immutable;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

  private static final String TIME_SLOT_PREFIX = "Time slot: ";
  private static final String ADDRESS_PREFIX = "Address: ";
  private static final String QUATED_TIME_PREFIX = "Quoted Time: ";

  public Task map(String taskStr) {
    List<String> lines = Arrays.asList(taskStr.split("\n"));
    return new Task(
        lines.get(0),
        lines.get(1),
        lines.get(2).replaceFirst(TIME_SLOT_PREFIX, ""),
        lines.get(3).replaceFirst(ADDRESS_PREFIX, ""),
        lines.get(4).replaceFirst(QUATED_TIME_PREFIX, ""),
        ImmutableList.copyOf(lines.subList(6, lines.size())));
  }
}
