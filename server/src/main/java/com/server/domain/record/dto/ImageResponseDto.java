package com.server.domain.record.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageResponseDto {
    private List<String> images;
}
