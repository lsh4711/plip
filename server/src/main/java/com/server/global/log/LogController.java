package com.server.global.log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@RestController
@RequestMapping("/logs")
public class LogController {
	@Value("${spring.servlet.multipart.location}")
	private String location;

	@GetMapping
	public ResponseEntity<StringBuilder> getLog() throws IOException { //리턴 타입 맞춰줌.
		location = location.substring(0, 15);
		location += "/spring.log";

		File file = new File(location);

		if (!file.exists()) {
			throw new CustomException(ExceptionCode.LOG_NOT_FOUND);
		}

		StringBuilder result = new StringBuilder();
		List<String> logs = FileUtils.readLines(file, "UTF-8");

		for (String log : logs) {
			result.append(log).append("<br />");
		}

		return ResponseEntity.ok(result);
	}
}
