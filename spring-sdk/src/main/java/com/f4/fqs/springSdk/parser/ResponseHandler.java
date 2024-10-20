package com.f4.fqs.springSdk.parser;

import com.f4.fqs.springSdk.exception.FQSException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseHandler {

    private final ObjectMapper objectMapper;

    public ResponseHandler() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> SuccessResponseBody<T> handleResponse(String responseBody, TypeReference<ResponseBody<T>> typeReference) {
        JsonNode jsonNode = parseJson(responseBody);
        ResponseBody<T> responseBodyObj = extractData(jsonNode, typeReference);
        return validateSuccess(responseBodyObj);
    }

    private JsonNode parseJson(String responseBody) {
        try {
            return objectMapper.readTree(responseBody);
        } catch (Exception e) {
            throw new FQSException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }

    private <T> SuccessResponseBody<T> validateSuccess(ResponseBody<T> responseBody) {
        if (responseBody instanceof FailedResponseBody failedResponse) {
            throw new FQSException("Error: " + failedResponse.getMsg());
        }
        return (SuccessResponseBody<T>) responseBody;
    }

    private <T> ResponseBody<T> extractData(JsonNode jsonNode, TypeReference<ResponseBody<T>> typeReference) {
        try {
            return objectMapper.treeToValue(jsonNode, typeReference);
        } catch (Exception e) {
            throw new FQSException("Failed to extract data: " + e.getMessage(), e);
        }
    }
}