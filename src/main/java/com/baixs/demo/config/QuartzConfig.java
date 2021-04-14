package com.baixs.demo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baixs.demo.job.SampleJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", "baixs");
        jobDataMap.put("age", 18);
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity(JobKey.jobKey("sampleJobName", "sampleJobGroup"))
                .withDescription("job description")
                .setJobData(jobDataMap)
                .storeDurably(true)
                //.requestRecovery(true)
                .build();
    }

    @Bean
    public Trigger trigger() {
        ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(10) // 定时任务间隔时间
                .repeatForever(); // 触发器无限循环触发
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity(TriggerKey.triggerKey("triggerKey", "triggerGroup"))
                .withSchedule(scheduleBuilder)
                .build();
        /*return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("myTrigger1","myTriggerGroup1")
                .usingJobData("job_trigger_param","job_trigger_param1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? *"))
                .build();*/
    }

    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.datasource")
    DataSource quartzDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
