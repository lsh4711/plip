package com.server.domain.oauth.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class KakaoTemplateObject {
	@Builder
	public static class Feed {
		private String object_type = "feed";
		// private Object item_content;
		private Content content;
		// private Social social;
		private String button_title;
		private Button[] buttons;
	}

	@Builder
	public static class Text {
		private String object_type;
		private String text;
		private Link link;
	}

	public static class Location {
		private String object_type;
		private String address;
		private String address_title;
		private Content content;
	}

	// 공용

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Content {
		private String title;
		private String description;
		private int image_width;
		private int image_height;
		private String image_url;
		private Link link;
	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Link {
		private String web_url = "https://plip.netlify.app/";
		private String mobile_web_url = "https://plip.netlify.app/";
		// private String android_execution_params = "";
		// private String ios_execution_params = "";
	}

	public static class Social {

	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Button {
		private String title;
		private Link link;
	}
}
