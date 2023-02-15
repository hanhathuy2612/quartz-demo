package com.example.quatzdemo.schedule.service;

import com.example.quatzdemo.schedule.model.JobModel;
import com.example.quatzdemo.schedule.model.TriggerModel;
import org.quartz.JobDetail;
import org.quartz.Trigger;

public interface IScheduleService {
    JobDetail buildJobDetail(JobModel jobModel);

    Trigger buildTrigger(TriggerModel triggerModel);
    void schedule(JobDetail jobDetail, Trigger trigger);
}
