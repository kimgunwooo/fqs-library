package com.f4.fqs.springSdk.config;

import com.f4.fqs.springSdk.constants.FQSSdk;
import com.f4.fqs.springSdk.exception.FQSException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.f4.fqs.springSdk.constants.FQSSdk.FQS_SERVER_URL;
import static com.f4.fqs.springSdk.constants.FQSSdk.SECRET_KEY;
import static com.f4.fqs.springSdk.constants.FQSSdk.VALIDATE_ENDPOINT;

@EnableConfigurationProperties(FQSSdkProperties.class)
@Configuration
public class FQSSdkConfig {

    private static final Logger log = LoggerFactory.getLogger(FQSSdkConfig.class);
    private final FQSSdkProperties fqsSdkProperties;
    private final HttpClient client;
    private String endpointUrl;

    public FQSSdkConfig(FQSSdkProperties fqsSdkProperties) {
        this.fqsSdkProperties = fqsSdkProperties;
        this.client = HttpClient.newHttpClient();
    }

    @PostConstruct
    public void initializeEndpointUrlUsingSecretKey() {
        log.info("FQS Library Enable");
        try {
            String response = sendValidationRequest();
            handleResponse(response);
        } catch (Exception e) {
            throw new FQSException("Failed to initialize endpoint URL: " + e.getMessage(), e);
        }
    }

    private String sendValidationRequest() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(createValidationUri())
            .header(SECRET_KEY, fqsSdkProperties.getSecretKey())
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private URI createValidationUri() throws Exception {
        return new URI(FQS_SERVER_URL + VALIDATE_ENDPOINT + "?queueName=" + fqsSdkProperties.getQueueName());
    }

    private void handleResponse(String response) {
        if ("false".equals(response)) {
            throw new FQSException("Queue name validation failed: " + fqsSdkProperties.getQueueName());
        }
        if ("true".equals(response)) {
            endpointUrl = FQS_SERVER_URL + "/" + fqsSdkProperties.getQueueName() + "/queue";
            log.debug("Endpoint URL is {}", endpointUrl);
        } else {
            throw new FQSException("Unexpected response from FQS server: " + response);
        }
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }
}
