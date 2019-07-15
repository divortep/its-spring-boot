package com.itsspringboot.mapper;


import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.itsspringboot.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TaskMapperTest {

  private TaskMapper taskMapper;

  @Before
  public void setUp() {
    taskMapper = new TaskMapper();
  }

  @Test
  public void testMapMethod() {
    String taskStr = "LDN A2\n"
        + "Friday, 28th of June\n"
        + "Time slot: 10:00AM - 12:00PM\n"
        + "Address: Front Ln, Upminster RM14 1LH, UK\n"
        + "Quoted Time: 2:00 hrs\n"
        + "To assemble:\n"
        + "1x 7+ Drw Chest of Drawers\n"
        + "1x Bedframe\n"
        + "\n";

    Task task = taskMapper.map(taskStr);
    assertNotNull(task);
    assertEquals(task.getNumber(), "LDN A2");
    assertEquals(task.getDate(), "Friday, 28th of June");
    assertEquals(task.getTimeSlot(), "10:00AM - 12:00PM");
    assertEquals(task.getAddress(), "Front Ln, Upminster RM14 1LH, UK");
    assertEquals(task.getQuotedTime(), "2:00 hrs");
    assertThat(task.getItems(), hasItem("1x 7+ Drw Chest of Drawers"));
    assertThat(task.getItems(), hasItem("1x Bedframe"));
  }
}
