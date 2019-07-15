package com.itsspringboot.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.itsspringboot.mapper.TaskMapper;
import com.itsspringboot.model.Task;
import com.itsspringboot.repository.impl.TaskDocumentRepositoryImpl;
import com.itsspringboot.service.EmailNotificationService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TaskDocumentRepositoryImpl.class})
public class TaskDocumentRepositoryImplTest {

  public static final String RESPONSE_FILE_NAME = "response.txt";
  public static final String URL = "http://domain.com";

  String response;
  private TaskMapper taskMapper = new TaskMapper();

  @Mock
  private JavaMailSender mailSender;

  @Spy
  private EmailNotificationService emailNotificationService = new EmailNotificationService(mailSender);

  @Spy
  private RestTemplate restTemplate = new RestTemplate();

  private TaskDocumentRepository taskDocumentRepository;

  @Before
  public void setUp() throws IOException {
    TaskDocumentRepository repository = new TaskDocumentRepositoryImpl(restTemplate, taskMapper, emailNotificationService);
    taskDocumentRepository = PowerMockito.spy(repository);
    ReflectionTestUtils.setField(taskDocumentRepository, "documentUrl", URL);

    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(RESPONSE_FILE_NAME).getFile());
    response = FileUtils.readFileToString(file, "UTF-8");
  }

  @Test
  public void testGetTasks() throws Exception {
    when(restTemplate.getForEntity(URL, String.class))
        .thenReturn(new ResponseEntity("", HttpStatus.OK));

    PowerMockito.doReturn(response).when(taskDocumentRepository, "collectTasksBlocks", "");
    List<Task> tasks = taskDocumentRepository.getTasks();

    verify(emailNotificationService, never()).notifyAdmin(any(), any());
    assertEquals(7, tasks.size());
  }
}
