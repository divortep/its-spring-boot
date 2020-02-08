package com.itsspringboot.service

import com.google.common.collect.ImmutableList
import com.itsspringboot.exception.AppException
import com.itsspringboot.model.*
import com.itsspringboot.repository.TaskRepository
import com.itsspringboot.repository.impl.TaskRepositoryImpl
import spock.lang.Specification

class TaskServiceTest extends Specification {

    TaskService taskService
    TaskRepository taskRepository
    EmailNotificationService emailNotificationService
    UserService userService

    def setup() {
        taskRepository = Mock(TaskRepositoryImpl)
        emailNotificationService = Mock(EmailNotificationService)
        userService = Mock(UserService);
        taskService = new TaskService(taskRepository, emailNotificationService, userService)
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
        given:
        def user = new User(null, "", "", "", "", ImmutableList.of(Role.ROLE_USER),
                new UserSettings())

        when:
        taskService.acceptTask("id", false)

        then:
        taskRepository.getTask(_) >> []
        userService.getCurrentUser() >> Optional.of(user)

        AppException ex = thrown()
        ex.message.contains("Task can't be found with id")
    }

    def "test notifyIKEA without teammate"() {
        given:
        def message

        when:
        taskService.notifyIKEA("tastid", new Performer(name: "name1", email: "email1"), null)

        then:
        1 * emailNotificationService.notifyIKEA(*_) >> { arguments -> message = arguments[1] }
        message == "Me (email1) please."
    }

    def "test notifyIKEA with teammate"() {
        given:
        def message

        when:
        taskService.notifyIKEA("tastid",
                new Performer(name: "name1", email: "email1"),
                new Performer(name: "name2", email: "email2"))

        then:
        1 * emailNotificationService.notifyIKEA(*_) >> { arguments -> message = arguments[1] }
        message == "Me (email1) and name2 (email2) please."
    }
}