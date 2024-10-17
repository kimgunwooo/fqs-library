package com.f4.fqs_library.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class FqsLoggingConfig {
    private static final Logger logger = LoggerFactory.getLogger(FqsLoggingConfig.class);

    @PostConstruct
    public void logInitialization() {
        logger.info("----FQS Library Initialization Started----");
    }
}
