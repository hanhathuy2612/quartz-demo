package com.example.quatzdemo.email;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
public class EmailSchedulerController {
    private final Scheduler scheduler;

    public EmailSchedulerController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("schedule/email")
    public ResponseEntity<EmailResponse> scheduleEmail(@Valid @RequestBody EmailRequest request) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(
                    request.getDateTime(),
                    request.getZoneId()
            );
            if (dateTime.isBefore(ZonedDateTime.now())) {
                return ResponseEntity.status(
                        HttpStatus.BAD_REQUEST
                ).body(
                        EmailResponse.builder()
                                .success(false)
                                .message("Datetime must be after current time")
                                .build()
                );
            }
            JobDetail detail = buildJobDetail(request);
            Trigger trigger = buildTrigger(detail, dateTime);
            scheduler.scheduleJob(detail, trigger);
            return ResponseEntity.ok(
            ).body(
                    EmailResponse.builder()
                            .success(true)
                            .jobId(detail.getKey().getName())
                            .jobGroup(detail.getKey().getGroup())
                            .message("Email scheduled successfully")
                            .build()
            );
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    EmailResponse.builder()
                            .success(false)
                            .message("Schedule Email Fail")
                            .build()
            );
        }
    }

    private JobDetail buildJobDetail(EmailRequest request) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email", request.getEmail());
        jobDataMap.put("subject", request.getSubject());
        jobDataMap.put("body", request.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(
                        UUID.randomUUID().toString(), "email-job"
                )
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withRepeatCount(5)
                                .withIntervalInSeconds(5)
                                .withMisfireHandlingInstructionFireNow()
                )
                .build();
    }
}
