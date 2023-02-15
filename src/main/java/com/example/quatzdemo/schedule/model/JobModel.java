package com.example.quatzdemo.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class JobModel {
    private JobDataMap jobDataMap;
    private String group;
    private String name;
    private String description;
    private Class<? extends Job> jobClass;
}
