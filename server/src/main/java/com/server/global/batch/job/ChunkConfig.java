package com.server.global.batch.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.server.domain.schedule.entity.Schedule;
import com.server.global.batch.parameter.CustomJobParameter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ChunkConfig {
    private int chunkSize = 10;

    private static final String JOB_NAME = "customJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CustomJobParameter customJobParameter;

    @Bean(JOB_NAME + "Parameter")
    @JobScope
    public CustomJobParameter createJobParameter() {
        return new CustomJobParameter();
    }

    @Bean
    public Job getCustomJob() {
        Job customJob = jobBuilderFactory.get(JOB_NAME)
                .start(getCustomStep())
                .build();

        return customJob;
    }

    // @Bean
    // @JobScope
    public Step getCustomStep() {
        Step customStep = stepBuilderFactory.get(JOB_NAME + "Step")
                .<Schedule, Schedule>chunk(chunkSize)
                .reader(getCustomReader())
                // .processor(null)
                .writer(getCustomWriter())
                // .faultTolerant()
                // .retry(Exception.class) // 알림 전송 실패 시
                // .noRollback(Exception.class) // test
                // .retryLimit(2) // 3번까지 시도(청크의 처음부터 시작), 보냈던 알림이 다시 전송된다.
                .build();

        return customStep;
    }

    // @Bean
    // @StepScope
    public JpaPagingItemReader<Schedule> getCustomReader() {
        // Map<String, Object> parameter = new HashMap<>();
        // parameter.put("date", null);

        JpaPagingItemReader<Schedule> reader = new JpaPagingItemReaderBuilder<Schedule>()
                .name(JOB_NAME + "Reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT s FROM Schedule s ORDER BY id") // 정렬 필수
                .build();

        return reader;
    }

    // Schedule 테이블을 이용하면 이미 알림을 보냈던 일정을 계속 확인하게 되므로
    // 나중에 알림을 보낼 행만 별도의 테이블에서 관리
    // @Bean
    // @StepScope
    // public ItemProcessor<Schedule, Schedule> processor() {
    //     return null;
    // }

    // @Bean
    // @StepScope
    // public JpaItemWriter<Schedule> writer() {
    public ItemWriter<Schedule> getCustomWriter() {
        // JpaItemWriter<Schedule> writer = new JpaItemWriterBuilder<Schedule>()
        //         .entityManagerFactory(entityManagerFactory)
        //         .build();

        // return writer;
        return list -> {
            for (Schedule schedule : list) {
                long test = schedule.getScheduleId();
                for (int i = 0; i < 3; i++) {
                    if (test > 11 && test < 15) {
                        System.out.println("에러: " + test);
                        continue;
                    }
                    System.out.println(customJobParameter.getDate());
                    System.out.printf("scheduleId: %d\n", schedule.getScheduleId());
                    break;
                }

            }
        };
    }
}