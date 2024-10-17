package com.f4.fqs_library.config;

import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "f4.fqs")
public class FQSClientProperties {

    private String secretKey;

    private String queueName;

    private Level level = Level.WARN;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Level getLevel() {
        return level;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
