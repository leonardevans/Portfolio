package com.lemutugi.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Map<String , ?> data = new HashMap<>();
    private Map<String , ?> errors = new HashMap<>();

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
