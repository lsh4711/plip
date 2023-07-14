package com.server.domain.record.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponseDto {
    private int size;
    private List<String> images;
}
