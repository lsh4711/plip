package com.server.domain.record.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//이미지 조회 시 사용
@Getter
@Setter
@Builder
public class ImageResponseDto {
    private int size;
    private List<String> images;
}
