package com.f4.fqs.springSdk.sdk;

import com.f4.fqs.springSdk.config.FQSSdkProperties;
import com.f4.fqs.springSdk.exception.FQSException;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.f4.fqs.springSdk.constants.FQSConstants.*;

@Component
public class FQSHttpClient {

    private final FQSSdkProperties fqsSdkProperties;
    private final HttpClient client;

    public FQSHttpClient(FQSSdkProperties fqsSdkProperties) {
        this.client = HttpClient.newHttpClient();
        this.fqsSdkProperties = fqsSdkProperties;
    }

    public HttpResponse<String> sendHttpRequest(String url, String method, @Nullable String requestBody) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(SECRET_KEY, fqsSdkProperties.getSecretKey());

            switch (method.toUpperCase()) {
                case POST:
                    requestBuilder
                            .header(CONTENT_TYPE, APPLICATION_JSON)
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody != null ? requestBody : "{}"));
                    break;

                case GET:
                    requestBuilder.GET();
                    break;

                default:
                    throw new FQSException("Unsupported HTTP method: " + method);
            }

            HttpRequest request = requestBuilder.build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new FQSException("HTTP request failed: " + e.getMessage(), e);
        }
    }
}