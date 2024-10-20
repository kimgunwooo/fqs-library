package com.f4.fqs.javaSdk.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlBuilder {

    private final StringBuilder baseUrl;
    private final StringBuilder params;

    private UrlBuilder(String baseUrl) {
        this.baseUrl = new StringBuilder(baseUrl);
        this.params = new StringBuilder();
    }

    public static UrlBuilder builder(String baseUrl) {
        return new UrlBuilder(baseUrl);
    }

    // add url path
    public UrlBuilder path(String path) {
        if (!baseUrl.isEmpty() && baseUrl.charAt(baseUrl.length() - 1) != '/') {
            baseUrl.append("/");
        }
        baseUrl.append(path);
        return this;
    }

    // add param
    public UrlBuilder param(String key, String value) {
        if (!params.isEmpty()) {
            params.append("&");
        }
        params.append(URLEncoder.encode(key, StandardCharsets.UTF_8));
        params.append("=");
        params.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
        return this;
    }

    // return endpoint url string
    public String build() {
        if (!params.isEmpty()) {
            return baseUrl + "?" + params.toString();
        }
        return baseUrl.toString();
    }
}