package com.paco.clip.representation.response;

import lombok.Data;

@Data
public class ExceptionResponse {
    private int status;
    private String message;

    public ExceptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
