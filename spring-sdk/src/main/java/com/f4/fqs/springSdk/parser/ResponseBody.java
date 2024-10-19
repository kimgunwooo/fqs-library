package com.f4.fqs.springSdk.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "success")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SuccessResponseBody.class, name = "true"),
        @JsonSubTypes.Type(value = FailedResponseBody.class, name = "false")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract sealed class ResponseBody<T> permits SuccessResponseBody, FailedResponseBody {
    private String code;

    protected ResponseBody() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
