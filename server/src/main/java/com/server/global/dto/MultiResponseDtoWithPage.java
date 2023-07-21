package com.server.global.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MultiResponseDtoWithPage<T> {
	private List<T> data;
	private int totalElements;
	private int totalPages;
	private int currentPage;
	private int pageSize;

	public MultiResponseDtoWithPage(List<T> data, int totalElements, int currentPage, int pageSize) {
		this.data = data;
		this.totalElements = totalElements;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalPages = (int)Math.ceil((double)totalElements / pageSize);
	}
}
