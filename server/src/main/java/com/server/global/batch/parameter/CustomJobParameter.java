package com.server.global.batch.parameter;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomJobParameter {
    private LocalDate date;
    private int hour;

    @Value("#{jobParameters[date]}")
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    @Value("#{jobParameters[hour]}")
    public void setHour(int hour) {
        this.hour = hour;
    }
}
