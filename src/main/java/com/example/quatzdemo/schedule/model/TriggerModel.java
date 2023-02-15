package com.example.quatzdemo.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TriggerModel {
    private String description;
    private String group;
    private JobDetail jobDetail;
    private ZonedDateTime startAt;
    private SimpleScheduleBuilder scheduleBuilder;
}
