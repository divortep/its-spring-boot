package com.itsspringboot.repository

import com.itsspringboot.mapper.TaskMapper
import com.itsspringboot.repository.impl.TaskDocumentRepositoryImpl
import com.itsspringboot.service.EmailNotificationService
import org.apache.commons.io.FileUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class TaskDocumentRepositoryImplTest extends Specification {

    static def RESPONSE_FILE_NAME = "response.txt"

    TaskDocumentRepository repository
    EmailNotificationService emailNotificationService
    TaskMapper taskMapper
    RestTemplate restTemplate
    String response

    def setup() {
        emailNotificationService = Mock(EmailNotificationService)
        taskMapper = new TaskMapper()
        restTemplate = Mock(RestTemplate)
        repository = Spy(TaskDocumentRepositoryImpl,
                constructorArgs: [restTemplate, taskMapper, emailNotificationService])

        def responseFile = new File(getClass().getResource("/$RESPONSE_FILE_NAME").toURI())
        response = FileUtils.readFileToString(responseFile, "UTF-8")
    }

    def "test getTasks"() {
        given:
        def tasks

        when:
        tasks = repository.getTasks()

        then:
        1 * restTemplate.getForEntity(_, _) >> new ResponseEntity("", HttpStatus.OK)
        1 * repository.collectTasksBlocks(*_) >> response

        //verify
        0 * emailNotificationService.notifyAdmin(*_)
        tasks.size() == 7
    }
}