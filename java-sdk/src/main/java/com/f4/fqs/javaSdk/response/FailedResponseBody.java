package com.f4.fqs.javaSdk.response;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("false")
public final class FailedResponseBody extends ResponseBody<Void> {

    private String msg;

    protected FailedResponseBody() {
    }

    public FailedResponseBody(String code, String msg) {
        this.setCode(code);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
