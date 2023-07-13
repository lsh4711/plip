package com.server.global.log;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@RestController
@RequestMapping("/log")
public class LogController {
    @Value("${spring.servlet.multipart.location}")
    private String location;

    @GetMapping
    public ResponseEntity getLog() throws IOException {
        location = location.substring(0, 29);
        location += "/server/deploy.log";

        File file = new File(location);

        if (!file.exists()) {
            throw new CustomException(ExceptionCode.LOG_NOT_FOUND);
        }

        String logs = FileUtils.readFileToString(file, "UTF-8");

        return ResponseEntity.ok(logs);
    }
}
