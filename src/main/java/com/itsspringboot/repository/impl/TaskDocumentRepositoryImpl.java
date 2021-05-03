package com.itsspringboot.repository.impl;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.itsspringboot.mapper.TaskMapper;
import com.itsspringboot.model.Task;
import com.itsspringboot.repository.TaskDocumentRepository;
import com.itsspringboot.service.EmailNotificationService;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class TaskDocumentRepositoryImpl implements TaskDocumentRepository {

  private final static Logger logger = LoggerFactory.getLogger(TaskDocumentRepositoryImpl.class);
  private final static String TASKS_BLOCK_REGEXP = "\"s\":\"(.*?)\"";
  private final static String TASK_HEADER_REGEXP = "LDN\\s\\w\\d+\\s\\([\\w\\d]+\\)";
  private final static String TASK_REGEXP = "(" + TASK_HEADER_REGEXP + ".*?(\\n{2}|(?=LDN)))";
  private final static String SPECIAL_CHARS_REGEXP = "\u0011";

  @Value("${its.document.url}")
  private String documentUrl;

  private RestTemplate restTemplate;
  private TaskMapper taskMapper;
  private EmailNotificationService emailNotificationService;

  @Autowired
  public TaskDocumentRepositoryImpl(RestTemplate restTemplate,
                                    TaskMapper taskMapper,
                                    EmailNotificationService emailNotificationService) {
    this.restTemplate = restTemplate;
    this.taskMapper = taskMapper;
    this.emailNotificationService = emailNotificationService;
  }

  public List<Task> getTasks() {
    ResponseEntity<String> response = restTemplate.getForEntity(documentUrl, String.class);
    if (response.getStatusCode() != HttpStatus.OK) {
      logger.error("Error response code: " + response.getStatusCode());
      return Collections.emptyList();
    }

    String tasksStr = collectTasksBlocks(response.getBody());
    return extractTasks(tasksStr);
  }

  protected String collectTasksBlocks(String response) {
    return Pattern.compile(TASKS_BLOCK_REGEXP)
        .matcher(response)
        .results()
        .map(result -> result.group(1))
        .map(StringEscapeUtils::unescapeJava)
        .collect(joining(""));
  }

  private List<Task> extractTasks(String tasksStr) {
    long tasksNumber = Pattern.compile(TASK_HEADER_REGEXP)
        .matcher(tasksStr)
        .results()
        .count();

    List<Task> tasks = Pattern
        .compile(TASK_REGEXP, Pattern.MULTILINE | Pattern.DOTALL)
        .matcher(RegExUtils.replaceAll(tasksStr, SPECIAL_CHARS_REGEXP, ""))
        .results()
        .map(result -> result.group(1))
        .map(taskMapper::map)
        .collect(toUnmodifiableList());

    Long foundTasksNumber = Long.valueOf(tasks.size());
    if (!foundTasksNumber.equals(tasksNumber)) {
      notifyAdmin(tasksStr, tasksNumber, foundTasksNumber);
    }

    return tasks;
  }

  private void notifyAdmin(String tasksStr, long tasksNumber, Long foundTasksNumber) {
    String message = String.format("An error while parsing tasks: tasksNumber=%d, found=%d \n%s",
        tasksNumber,
        foundTasksNumber,
        tasksStr
    );
    emailNotificationService.notifyAdmin("An error while parsing tasks.", message);
  }
}
