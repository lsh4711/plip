package com.server.global.batch.tasklet;

import java.time.LocalDateTime;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BatchTasklet implements Tasklet, StepExecutionListener {
    private int order;

    public BatchTasklet(int order) {
        this.order = order;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        String result = String.format("BatchTasklet%d: %s", order, now);

        System.out.println(result);

        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("----------------------------------------");
    }

    @Override
    @Nullable
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("----------------------------------------");

        return ExitStatus.COMPLETED;
    }
}
