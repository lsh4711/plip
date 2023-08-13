package com.server.global.batch.job;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.push.service.PushService;
import com.server.domain.schedule.entity.Schedule;
import com.server.global.batch.parameter.CustomJobParameter;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

// Schedule 테이블을 이용하면 이미 알림을 보냈던 일정을 계속 확인하게 되므로
// 나중에 알림을 보낼 데이터만 별도의 테이블에서 관리

@Configuration
@RequiredArgsConstructor
public class ChunkConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CustomJobParameter customJobParameter;

    private final KakaoApiService kakaoApiService;
    private final PushService pushService;

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int chunkSize = 1;
    private static final String JOB_NAME = "customJob";

    // @Bean(JOB_NAME + "Parameter")
    @Bean
    @JobScope
    public CustomJobParameter createJobParameter() {
        return new CustomJobParameter();
    }

    @Bean
    public Job customJob() {
        Job customJob = jobBuilderFactory.get(JOB_NAME)
                .start(customStep())
                .build();

        return customJob;
    }

    @Bean
    @JobScope
    public Step customStep() {
        Step customStep = stepBuilderFactory.get(JOB_NAME + "Step")
                .<Schedule, Schedule>chunk(chunkSize)
                .reader(customReader())
                .processor(customProcessor())
                .writer(customWriter())
                .faultTolerant()
                .retry(Exception.class) // 전송 실패를 고려하여
                .retryLimit(2) // 총 n번까지 시도, 나중에 backoff policy 추가
                .noRollback(Exception.class) // n번 후 스킵
                // .skip(Exception.class)
                // .skipLimit(1)
                .build();

        return customStep;
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Schedule> customReader() {
        int hour = customJobParameter.getHour();
        LocalDate date = customJobParameter.getDate();

        String column = hour != 22 ? "startDate" : "endDate";
        String queryString = String.format(
            "%s%s%s%s",
            "SELECT s ",
            "FROM Schedule s ",
            String.format("WHERE s.%s = :date ", column),
            "ORDER BY id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", date);

        JpaPagingItemReader<Schedule> reader = new JpaPagingItemReaderBuilder<Schedule>()
                .name(JOB_NAME + "Reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString(queryString) // 정렬 필수
                .parameterValues(parameters)
                .build();

        return reader;
    }

    // @Bean(JOB_NAME + "Processor")
    // @Bean
    // @StepScope
    public ItemProcessor<Schedule, Schedule> customProcessor() {
        int hour = customJobParameter.getHour();

        return schedule -> {
            Member member = schedule.getMember();

            if (!member.getNickname().equals("이수희")) {
                throw new CustomException(ExceptionCode.TEST_CODE);
            }

            kakaoApiService.sendScheduledMessage(schedule, member, hour);
            pushService.sendScheduledMessage(schedule, member, hour);

            return schedule;
        };
    }

    // @Bean
    // @StepScope
    // public JpaItemWriter<Schedule> writer() {
    public ItemWriter<Schedule> customWriter() {
        // JpaItemWriter<Schedule> writer = new JpaItemWriterBuilder<Schedule>()
        //         .entityManagerFactory(entityManagerFactory)
        //         .build();

        return schedules -> {
            if (schedules.size() == 0) {
                logger.error("### 배치 작업 중 알림 전송 실패..");
                return;
            }
            long id = schedules.get(0).getScheduleId();
            logger.info("## 배치 작업 중 알림 전송 성공.. scheduleId: {}", id);
            // schedules.stream().forEach(
            //     schedule -> logger.info("## 알림 전송 성공: scheduleId: {}",
            //         schedule.getScheduleId()));
        };
    }
}
