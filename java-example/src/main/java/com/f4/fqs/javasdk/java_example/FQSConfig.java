package com.f4.fqs.javasdk.java_example;

import com.f4.fqs.javaSdk.config.FQSSdkConfig;
import com.f4.fqs.javaSdk.sdk.FQSSdk;
import com.f4.fqs.javaSdk.sdk.FQSSdkImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FQSConfig is a Spring configuration class that defines beans related to the FQS SDK.
 * This class is responsible for loading configuration properties from the application context
 * and creating an instance of the FQSSdk.
 */
@Configuration // Indicates that this class contains Spring bean definitions
public class FQSConfig {

    @Value("${f4.fqs.secretKey}") // Injects the secret key from application properties
    private String secretKey;

    @Value("${f4.fqs.queueName}") // Injects the queue name from application properties
    private String queueName;

    /**
     * Creates a bean of type FQSSdk.
     *
     * This method initializes an instance of FQSSdkImpl by using the configuration
     * values (secretKey and queueName) injected from the application properties.
     *
     * The FQSSdkImpl is initialized with a singleton instance of FQSSdkConfig,
     * ensuring that the SDK is properly configured for use throughout the application.
     *
     * @return an instance of FQSSdk configured with the provided secret key and queue name
     */
    @Bean // Indicates that this method returns a Spring bean
    public FQSSdk fqsSdk() {
        // Creates an instance of FQSSdkImpl using the FQSSdkConfig with injected properties
        return new FQSSdkImpl(FQSSdkConfig.getInstance(secretKey, queueName));
    }
}
