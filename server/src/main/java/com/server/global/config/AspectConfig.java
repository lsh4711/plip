package com.server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.server.global.log.RequestLoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
	@Bean
	public RequestLoggingAspect requestLoggingAspect() {
		return new RequestLoggingAspect();
	}
}
