package com.server.global.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;

import com.server.global.batch.tasklet.BatchTasklet;

import lombok.RequiredArgsConstructor;

// @Configuration
@RequiredArgsConstructor
public class TaskletConfig {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job batchJob() {
		Job customJob = jobBuilderFactory.get("jobName")
			.start(batchStep1(1)) // Step 설정
			.next(batchStep2(2))
			.next(batchStep3(3))
			.build();

		return customJob;
	}

	@Bean
	public Step batchStep1(int order) {
		Step customStep1 = stepBuilderFactory.get("stepName1")
			.tasklet(new BatchTasklet(order)) // Tasklet 설정
			.build();

		return customStep1;
	}

	@Bean
	public Step batchStep2(int order) {
		Step customStep2 = stepBuilderFactory.get("stepName2")
			.tasklet(new BatchTasklet(order)) // Tasklet 설정
			.build();

		return customStep2;
	}

	@Bean
	public Step batchStep3(int order) {
		Step customStep3 = stepBuilderFactory.get("stepName3")
			.tasklet(new BatchTasklet(order)) // Tasklet 설정
			.build();

		return customStep3;
	}

}
