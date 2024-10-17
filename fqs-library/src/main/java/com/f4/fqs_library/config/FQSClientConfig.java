package com.f4.fqs_library.config;

import com.f4.fqs_library.exception.FQSException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.f4.fqs_library.constants.FQSConstants.FQS_SERVER_URL;
import static com.f4.fqs_library.constants.FQSConstants.SECRET_KEY;
import static com.f4.fqs_library.constants.FQSConstants.VALIDATE_ENDPOINT;

@EnableConfigurationProperties(FQSClientProperties.class)
@Configuration
public class FQSClientConfig {

    private static final Logger log = LoggerFactory.getLogger(FQSClientConfig.class);
    private final FQSClientProperties fqsClientProperties;
    private final HttpClient client;
    private String endpointUrl;

    public FQSClientConfig(FQSClientProperties fqsClientProperties) {
        this.fqsClientProperties = fqsClientProperties;
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
            .header(SECRET_KEY, fqsClientProperties.getSecretKey())
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private URI createValidationUri() throws Exception {
        return new URI(FQS_SERVER_URL + VALIDATE_ENDPOINT + "?queueName=" + fqsClientProperties.getQueueName());
    }

    private void handleResponse(String response) {
        if ("false".equals(response)) {
            throw new FQSException("Queue name validation failed: " + fqsClientProperties.getQueueName());
        }
        if ("true".equals(response)) {
            endpointUrl = FQS_SERVER_URL + "/" + fqsClientProperties.getQueueName() + "/queue";
            log.debug("Endpoint URL is {}", endpointUrl);
        } else {
            throw new FQSException("Unexpected response from FQS server: " + response);
        }
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }
}
