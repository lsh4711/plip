package com.server.domain.place.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

public class PlaceDto {
	@Getter
	@Setter
	public static class Post {
		// @NotBlank // optional
		private long apiId;

		@NotBlank
		private String name;

		@NotBlank
		private String address;

		@NotNull
		@Pattern(regexp = "|^\\d{2,5}(-\\d{3,4})?(-\\d{4})?$")
		private String phone;

		@NotBlank
		private String latitude;

		@NotBlank
		private String longitude;

		@NotNull
		private String category;

		@NotNull
		private Boolean bookmark;
	}

	@Getter
	public static class Patch {

	}
}
