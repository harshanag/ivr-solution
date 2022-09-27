package com.example.ivrsolution.enums;

public enum HttpHeadersEnum {
    X_IBM_Client_Id("X-IBM-Client-Id"),
    CONTENT_TYPE("content-type"),
    ACCEPT("accept"),
    DATA("data"),
    APPLICATION_JSON("application/json");

    public final String value;

    HttpHeadersEnum(String value) {
        this.value = value;
    }
}
