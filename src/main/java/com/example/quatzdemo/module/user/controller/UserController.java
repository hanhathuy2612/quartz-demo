package com.example.quatzdemo.module.user.controller;

import com.example.quatzdemo.module.user.schedule.CreateUserJob;
import com.example.quatzdemo.schedule.model.JobModel;
import com.example.quatzdemo.schedule.model.TriggerModel;
import com.example.quatzdemo.schedule.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final IScheduleService iScheduleService;

    @GetMapping("create")
    public void createUsingSchedule() {
        JobDetail detail = iScheduleService.buildJobDetail(
                JobModel.builder()
                        .group("my-group")
                        .name(UUID.randomUUID().toString())
                        .description("my-description")
                        .jobDataMap(
                                new JobDataMap(
                                        Map.of(
                                                "email", "withuwe021@gmail.com",
                                                "name", "huyhn",
                                                "phoneNumber", "0344551543"
                                        )
                                )
                        )
                        .jobClass(CreateUserJob.class)
                        .build()
        );

        Trigger trigger = iScheduleService.buildTrigger(
                TriggerModel.builder()
                        .group("my-trigger-group")
                        .description("Trigger detail")
                        .jobDetail(detail)
                        .startAt(ZonedDateTime.now())
                        .scheduleBuilder(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withRepeatCount(5)
                                        .withIntervalInSeconds(5)
                        )
                        .build()
        );

        iScheduleService.schedule(detail, trigger);
    }
}
