package com.server.global.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

public class CustomUtil {
    public static long getAuthId() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null) {
            throw new CustomException(ExceptionCode.UNKNOWN_USER);
        }

        long memberId = Long.parseLong(authentication.getCredentials()
                .toString());

        return memberId;
    }
}
