package com.f4.fqs.library;

import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;

@Component
public class CustomRunner implements CommandLineRunner {

    private static final Log log = LogFactory.getLog(CustomRunner.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("*********************************");
        log.info("**************start**************");
        log.info("*********************************");
    }
}