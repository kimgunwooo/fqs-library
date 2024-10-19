package com.f4.fqs.springSdk.sdk;

import com.f4.fqs.springSdk.config.FQSSdkConfig;
import com.f4.fqs.springSdk.parser.ResponseHandler;
import com.f4.fqs.springSdk.parser.SuccessResponseBody;
import com.f4.fqs.springSdk.parser.UrlBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Nullable;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import static com.f4.fqs.springSdk.constants.FQSConstants.*;

public class FQSSdkImpl implements FQSSdk {

    private final FQSSdkConfig fqsSdkConfig;
    private final ResponseHandler responseHandler;
    private final FQSHttpClient fqsHttpClient;

    public FQSSdkImpl(FQSSdkConfig fqsSdkConfig, ResponseHandler responseHandler, FQSHttpClient fqsHttpClient) {
        this.fqsSdkConfig = fqsSdkConfig;
        this.responseHandler = responseHandler;
        this.fqsHttpClient = fqsHttpClient;
    }

    @Override
    public String enqueue() {
        String responseBody = sendRequestWithResponse(ENQUEUE_ENDPOINT, POST, null);
        SuccessResponseBody<String> successResponse =
                responseHandler.handleResponse(responseBody, new TypeReference<>() {});
        return successResponse.getData();
    }

    @Override
    public List<String> dequeue(Integer size) {
        String url = UrlBuilder.builder(DEQUEUE_ENDPOINT)
                .param(SIZE, size.toString())
                .build();

        String responseBody = sendRequestWithResponse(url, POST, null);
        SuccessResponseBody<List<String>> successResponse =
                responseHandler.handleResponse(responseBody, new TypeReference<>() {});
        return successResponse.getData();
    }

    @Override
    public Long retrieveQueueRank(UUID identifier) {
        String url = UrlBuilder.builder(RETRIEVE_QUEUE_ENDPOINT)
                .param(IDENTIFIER, identifier.toString())
                .build();

        String responseBody = sendRequestWithResponse(url, GET, null);
        SuccessResponseBody<Long> successResponse =
                responseHandler.handleResponse(responseBody, new TypeReference<>() {});
        return successResponse.getData();
    }

    private String sendRequestWithResponse(String path, String method, @Nullable String requestBody) {
        HttpResponse<String> response = fqsHttpClient.sendHttpRequest(fqsSdkConfig.getEndpointUrl() + path, method, requestBody);
        return response.body();
    }
}
