package com.server.global.batch.job;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

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
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.test.auth.KakaoAuth;
import com.server.global.batch.parameter.CustomJobParameter;

import lombok.RequiredArgsConstructor;

// Schedule 테이블을 이용하면 이미 알림을 보냈던 일정을 계속 확인하게 되므로
// 나중에 알림을 보낼 행만 별도의 테이블에서 관리

@Configuration
@RequiredArgsConstructor
public class ChunkConfig {
    private int chunkSize = 10;

    private static final String JOB_NAME = "customJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CustomJobParameter customJobParameter;

    private final KakaoAuth kakaoAuth;

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
                // .faultTolerant()
                // .retry(Exception.class) // 알림 전송 실패 시
                // .noRollback(Exception.class) // test
                // .retryLimit(2) // 3번까지 시도(청크의 처음부터 시작), 보냈던 알림이 다시 전송된다.
                .build();

        return customStep;
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Schedule> customReader() {
        LocalDate date = customJobParameter.getDate();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", date);

        JpaPagingItemReader<Schedule> reader = new JpaPagingItemReaderBuilder<Schedule>()
                .name(JOB_NAME + "Reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString(
                    "SELECT s FROM Schedule s WHERE s.startDate = :date AND s.member.kakaoToken IS NOT NULL ORDER BY id") // 정렬 필수
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
            long memberId = member.getMemberId();
            KakaoToken kakaoToken = member.getKakaoToken();
            String accessToken = kakaoToken.getAccessToken();
            if (hour == 7) {
                String message = "오늘은 설레는 여행날이네요.";
                kakaoAuth.sendMessage(accessToken, message);
            } else if (hour == 21) {
                String message = "내일은 설레는 여행날이네요.";
                kakaoAuth.sendMessage(accessToken, message);
            }

            return null;
        };
    }

    // @Bean
    // @StepScope
    // public JpaItemWriter<Schedule> writer() {
    public ItemWriter<Schedule> customWriter() {
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
                    // System.out.println(customJobParameter.getDate());
                    System.out.printf("scheduleId: %d\n", schedule.getScheduleId());
                    break;
                }

            }
        };
    }
}
