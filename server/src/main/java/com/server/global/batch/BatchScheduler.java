package com.server.global.batch;

import java.time.LocalDate;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.server.global.batch.job.ChunkConfig;

import lombok.RequiredArgsConstructor;

// @Async
@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final ChunkConfig chunkConfig;

    @Scheduled(cron = "0 0 21 * * *")
    public void runJobAt21() {
        LocalDate date = LocalDate.now().plusDays(1);

        runJob(date, 21);
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void runJobAt7() {
        LocalDate date = LocalDate.now();

        runJob(date, 7);
    }

    public void runJob(LocalDate date, long hour) {
        JobParameters parameters = new JobParametersBuilder()
                .addString("date", date.toString())
                .addLong("hour", hour)
                .toJobParameters();

        try {
            jobLauncher.run(chunkConfig.customJob(), parameters);
        } catch (JobExecutionAlreadyRunningException
                | JobRestartException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
            System.out.println("에러");
        }

    }
}
