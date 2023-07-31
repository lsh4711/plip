package com.server.global.log.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@Service
public class LogService {
    @Value("${log.path}")
    private String logPath;

    public StringBuilder getLog(String logName) {
        String fullPath = String.format("%s/%s.log",
            logPath,
            logName);
        File file = new File(fullPath);

        if (!file.exists()) {
            throw new CustomException(ExceptionCode.LOG_NOT_FOUND);
        }

        StringBuilder result = new StringBuilder();

        try {
            List<String> logs = FileUtils.readLines(file, "UTF-8");
            for (String log : logs) {
                result.append(log).append("<br />");
            }
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.LOG_NOT_FOUND);
        }

        return result;
    }
}
