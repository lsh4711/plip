package com.server.global.event.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class Notice {
    @NotBlank
    private String message;
}
