package com.f4.fqs.javaSdk.parser;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("true")
public final class SuccessResponseBody<T> extends ResponseBody<T> {
    private final T data;

    public SuccessResponseBody() {
        data = null;
    }

    public SuccessResponseBody(T result) {
        this.data = result;
    }

    public T getData() {
        return data;
    }
}
