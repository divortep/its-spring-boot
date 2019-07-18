package com.itsspringboot.service

import com.itsspringboot.exception.AppException
import com.itsspringboot.model.AcceptedTask
import com.itsspringboot.model.Task
import com.itsspringboot.repository.TaskRepository
import com.itsspringboot.repository.impl.TaskRepositoryImpl
import spock.lang.Specification


class TaskServiceTest extends Specification {

    TaskService taskService
    TaskRepository taskRepository
    EmailNotificationService emailNotificationService

    def setup() {
        taskRepository = Mock(TaskRepositoryImpl)
        emailNotificationService = Mock(EmailNotificationService)
        taskService = new TaskService(taskRepository, emailNotificationService)
    }

    def "test testGetAvailableTasks"() {
        given:
        def availableTasks = [new Task(id: "1"), new Task(id: "2")]
        def acceptedTasks = [new AcceptedTask(id: "3")]
        def resultTasks

        when:
        resultTasks = taskService.getAvailableTasks()

        then:
        1 * taskRepository.getTasks() >> availableTasks
        1 * taskRepository.getAcceptedTasks() >> acceptedTasks

        resultTasks.size() == 2
        resultTasks[0].id == "1"
        resultTasks[1].id == "2"
    }

    def "test acceptTask without id"() {
        when:
        taskService.acceptTask(null, false)

        then:
        AppException ex = thrown()
        ex.message == "Task id can't be empty"
    }

    def "test acceptTask wrong id"() {
        when:
        taskService.acceptTask("id", false)

        then:
        taskRepository.getTask(_) >> []

        AppException ex = thrown()
        ex.message.contains("Task not found with id")
    }
}