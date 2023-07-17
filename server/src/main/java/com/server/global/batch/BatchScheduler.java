package com.server.global.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;

import com.server.global.batch.job.ChunkConfig;

import lombok.RequiredArgsConstructor;

// @Async
// @Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final ChunkConfig chunkConfig;

    @Scheduled(cron = "30 22 2 * * *")
    public void testtest() {
        System.out.println(LocalDateTime.now());
        System.out.println("testtesttesttesttesttesttesttest");
    }

    @Scheduled(fixedDelay = 1000)
    public void test() {
        // JobParameter parameter = new JobParameter()
        // .add
        Map<String, JobParameter> map = new HashMap<>();
        // map.put("key", parameter);

        JobParameters parameters = new JobParameters(map);
        try {
            jobLauncher.run(chunkConfig.batchJob(), parameters);
        } catch (JobExecutionAlreadyRunningException
                | JobRestartException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
            System.out.println("에러");
        }
        System.out.println(LocalDateTime.now().withNano(0));
    }

    // @Scheduled(cron = "0 0 21 * * *")
    public void runJobAt21() {
        LocalDate day = LocalDate.now().plusDays(1);
    }

    // @Scheduled(cron = "0 0 7 * * *")
    public void runJobAt7() {
        LocalDate day = LocalDate.now();
    }
}
