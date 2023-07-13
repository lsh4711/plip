package com.server.domain.test.auth;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String refresh_token_expires_in;
    private Date accessExpiration;
    private Date refreshExpiration;
}
