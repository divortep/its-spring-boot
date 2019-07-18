package com.itsspringboot.mapper

import com.itsspringboot.model.Task
import spock.lang.Specification


class TaskMapperTest extends Specification {

    TaskMapper taskMapper

    def setup() {
        taskMapper = new TaskMapper()
    }

    def "test map method"() {
        given:
        Task task
        def taskStr = """\
            LDN A2
            Friday, 28th of June
            Time slot: 10:00AM - 12:00PM
            Address: Front Ln, Upminster RM14 1LH, UK
            Quoted Time: 2:00 hrs
            To assemble:
            1x 7+ Drw Chest of Drawers
            1x Bedframe
            
            """

        when:
        task = taskMapper.map(taskStr.stripIndent());

        then:
        task != null
        task.number == "LDN A2"
        task.date == "Friday, 28th of June"
        task.timeSlot == "10:00AM - 12:00PM"
        task.address == "Front Ln, Upminster RM14 1LH, UK"
        task.quotedTime == "2:00 hrs"
        task.items.size() == 2
        task.items.contains("1x 7+ Drw Chest of Drawers")
        task.items.contains("1x Bedframe")
    }
}