package com.example.quatzdemo.module.user.controller;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final Scheduler scheduler;

    public UserController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GetMapping("/scheduleJob")
    public String scheduleJob() throws SchedulerException {
        JobKey jobKey = new JobKey("myJob");
        if (scheduler.checkExists(jobKey)) {
            return "Job already scheduled";
        } else {
            scheduler.triggerJob(jobKey);
            return "Job scheduled successfully";
        }
    }
}
