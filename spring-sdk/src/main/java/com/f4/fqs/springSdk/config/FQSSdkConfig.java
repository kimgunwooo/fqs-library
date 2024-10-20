package com.f4.fqs.springSdk.config;

import com.f4.fqs.springSdk.exception.FQSException;
import com.f4.fqs.springSdk.utils.UrlBuilder;
import com.f4.fqs.springSdk.utils.FQSHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.f4.fqs.springSdk.constants.FQSConstants.*;

@EnableConfigurationProperties(FQSSdkProperties.class)
@Configuration
public class FQSSdkConfig {

    private static final Logger log = LoggerFactory.getLogger(FQSSdkConfig.class);
    private final FQSSdkProperties fqsSdkProperties;
    private final FQSHttpClient httpClient;
    private final String endpointUrl;
    private final String secretKey;

    public FQSSdkConfig(FQSSdkProperties fqsSdkProperties) {
        this.fqsSdkProperties = fqsSdkProperties;
        this.httpClient = new FQSHttpClient();
        this.endpointUrl = initializeEndpointUrl();
        this.secretKey = fqsSdkProperties.getSecretKey();
    }

    private String initializeEndpointUrl() {
        try {
            log.info("FQS Library Enable");
            httpClient.sendHttpRequest(createValidationUri(), GET, null, fqsSdkProperties.getSecretKey());
            String url = UrlBuilder.builder("http://localhost:19096")
                    .path(fqsSdkProperties.getQueueName())
                    .path(QUEUE_MANAGE_ENDPOINT)
                    .build();
            log.info("endpoint url : {}", url);
            return url;
        } catch (Exception e) {
            throw new FQSException("Failed to initialize endpoint URL: " + e.getMessage(), e);
        }
    }

    private String createValidationUri() throws Exception {
        String url = UrlBuilder.builder(FQS_SERVER_URL + VALIDATE_ENDPOINT)
                .param(QUEUE_NAME, fqsSdkProperties.getQueueName())
                .build();
        log.info("url : {}", url);
        return url;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }
}