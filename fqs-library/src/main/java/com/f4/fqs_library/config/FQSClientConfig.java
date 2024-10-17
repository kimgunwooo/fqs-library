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

@EnableConfigurationProperties(FQSClientProperties.class)
@Configuration
public class FQSClientConfig {

    public static final String SECRET_KEY = "secretKey";
    private static final String FQS_SERVER_URL = "http://localhost:19095/api/queue/endpoint-url";

    private static final Logger log = LoggerFactory.getLogger(FQSClientConfig.class);
    private final FQSClientProperties fqsClientProperties;
    private String endpointUrl;

    public FQSClientConfig(FQSClientProperties fqsClientProperties) {
        this.fqsClientProperties = fqsClientProperties;
    }

    @PostConstruct
    public void initializeEndpointUrlUsingSecretKey() {
        log.info("FQS Library Enable");
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(FQS_SERVER_URL))
                    .header(SECRET_KEY, fqsClientProperties.getSecretKey())
                    .GET()
                    .build();
            log.info("request : {}", request);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("response : {}", response);
            endpointUrl = response.body();
        } catch (Exception e) {
            throw new FQSException("Failed to initialize endpoint URL", e);
        }
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }
}
