package com.f4.fqs.javaSdk.config;

import com.f4.fqs.javaSdk.exception.FQSException;
import com.f4.fqs.javaSdk.utils.UrlBuilder;
import com.f4.fqs.javaSdk.utils.FQSHttpClient;

import static com.f4.fqs.javaSdk.constants.FQSConstants.*;

public class FQSSdkConfig {

    private static FQSSdkConfig instance;
    private final FQSHttpClient httpClient;
    private final String endpointUrl;
    private final String secretKey;
    private final String queueName;

    private FQSSdkConfig(String secretKey, String queueName) {
        this.secretKey = secretKey;
        this.queueName = queueName;
        this.httpClient = new FQSHttpClient();
        this.endpointUrl = initializeEndpointUrl();
    }

    public static synchronized FQSSdkConfig getInstance(String secretKey, String queueName) {
        if (instance == null) {
            instance = new FQSSdkConfig(secretKey, queueName);
        }
        return instance;
    }

    private String initializeEndpointUrl() {
        try {
            httpClient.sendHttpRequest(createValidationUri(), GET, null, secretKey);
            return UrlBuilder.builder("http://localhost:19096")
                    .path(queueName)
                    .path(QUEUE_MANAGE_ENDPOINT)
                    .build();
        } catch (Exception e) {
            throw new FQSException("Failed to initialize endpoint URL: " + e.getMessage(), e);
        }
    }

    private String createValidationUri() throws Exception {
        return UrlBuilder.builder(FQS_SERVER_URL + VALIDATE_ENDPOINT)
                .param(QUEUE_NAME, queueName)
                .build();
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
