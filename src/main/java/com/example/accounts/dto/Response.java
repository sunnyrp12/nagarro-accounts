package com.example.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {

    @JsonProperty("error_status")
    private boolean errorStatus;
    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    public Response(boolean errorStatus, String message, Object data) {
        this.errorStatus = errorStatus;
        this.message = message;
        this.data = data;
    }
}
