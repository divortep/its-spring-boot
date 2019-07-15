package com.itsspringboot.repository;

import com.itsspringboot.model.Task;
import java.util.List;

public interface TaskDocumentRepository {

  List<Task> getTasks();
}
