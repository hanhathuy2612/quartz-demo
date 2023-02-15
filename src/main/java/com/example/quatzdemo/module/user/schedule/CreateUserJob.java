package com.example.quatzdemo.module.user.schedule;

import com.example.quatzdemo.module.user.entity.UserEntity;
import com.example.quatzdemo.module.user.repository.UserRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CreateUserJob implements Job {
    private final UserRepository userRepository;

    public CreateUserJob(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        UserEntity userEntity = UserEntity.builder()
                .name(jobDataMap.getString("name"))
                .email(jobDataMap.getString("email"))
                .phoneNumber(jobDataMap.getString("phoneNumber"))
                .build();
        userRepository.save(userEntity);
    }
}
