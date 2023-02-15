package com.example.quatzdemo.schedule.service;

import com.example.quatzdemo.schedule.model.JobModel;
import com.example.quatzdemo.schedule.model.TriggerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final Scheduler scheduler;

    @Override
    public JobDetail buildJobDetail(JobModel jobModel) {
        return JobBuilder.newJob(jobModel.getJobClass())
                .withIdentity(
                        jobModel.getName(), jobModel.getGroup()
                )
                .withDescription(jobModel.getDescription())
                .usingJobData(jobModel.getJobDataMap())
                .storeDurably()
                .build();
    }

    @Override
    public Trigger buildTrigger(TriggerModel triggerModel) {
        return TriggerBuilder.newTrigger()
                .forJob(triggerModel.getJobDetail())
                .withIdentity(triggerModel.getJobDetail().getKey().getName(), triggerModel.getGroup())
                .withDescription(triggerModel.getDescription())
                .startAt(
                        Date.from(triggerModel.getStartAt().toInstant())
                )
                .withSchedule(
                        triggerModel.getScheduleBuilder()
                )
                .build();
    }

    @Override
    public void schedule(JobDetail jobDetail, Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            try {
                scheduler.resumeJob(jobDetail.getKey());
            } catch (SchedulerException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
