package com.server.global.config;

import com.server.global.log.RequestLoggingAspect;

// @Configuration
// @EnableAspectJAutoProxy
public class AspectConfig {
    // @Bean
    public RequestLoggingAspect requestLoggingAspect() {
        return new RequestLoggingAspect();
    }
}
