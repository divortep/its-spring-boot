package com.itsspringboot.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.itsspringboot.exception.AppException;
import com.itsspringboot.model.AcceptedTask;
import com.itsspringboot.model.Task;
import com.itsspringboot.repository.TaskRepository;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class TaskServiceTest {

  @Mock
  private TaskRepository taskRepository;
  @Mock
  private EmailNotificationService emailNotificationService;

  private TaskService taskService;

  @Before
  public void setUp() {
    taskService = new TaskService(taskRepository, emailNotificationService);
  }

  @Test
  public void testGetAvailableTasks() {
    Task task1 = Mockito.mock(Task.class);
    when(task1.getId()).thenReturn("id1");

    Task task2 = Mockito.mock(Task.class);
    when(task2.getId()).thenReturn("id2");

    AcceptedTask acceptedTask = Mockito.mock(AcceptedTask.class);
    when(acceptedTask.getId()).thenReturn("id1");

    List<Task> allTasks = Lists.newArrayList(task1, task2);
    when(taskRepository.getTasks()).thenReturn(allTasks);
    when(taskRepository.getAcceptedTasks()).thenReturn(Lists.newArrayList(acceptedTask));

    List<Task> availableTasks = taskService.getAvailableTasks();
    assertEquals(1, availableTasks.size());
    assertEquals("id2", availableTasks.get(0).getId());
  }

  @Test(expected = AppException.class)
  public void testAcceptTaskWithoutId() {
    taskService.acceptTask(null, false);
  }

  @Test(expected = AppException.class)
  public void testAcceptTaskWithWrongId() {
    taskService.acceptTask("id", false);
  }
}
