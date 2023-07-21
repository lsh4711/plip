package com.server.global.doc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocController {
	@Value("${kakao.redirect-url}")
	private String url;

	@GetMapping
	public void getDocs(HttpServletResponse response) throws IOException {
		response.sendRedirect(url + "/docs/swagger-ui/index.html");
	}
}
