package com.pretest.dott.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pretest.dott.common.enums.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int status;
    private String code;
    private String message;
    private T data;
    private List<ApiFieldError> errors;

    public static <T> ApiResponse<T> of(IResponseCode responseCode) {
        return new ApiResponse<>(responseCode);
    }

    public static <T> ApiResponse<T> of(IResponseCode responseCode, T data) {
        return new ApiResponse<>(responseCode, data);
    }

    public static <T> ApiResponse<T> of(IResponseCode responseCode, String message) {
        return new ApiResponse<>(responseCode, message);
    }

    public static <T> ApiResponse<T> of(IResponseCode responseCode, List<FieldError> fieldErrors) {
        return new ApiResponse<>(responseCode, fieldErrors);
    }

    private ApiResponse(IResponseCode responseCode) {
        this.status = responseCode.getStatus();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    private ApiResponse(IResponseCode responseCode, T data) {
        this.status = responseCode.getStatus();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    private ApiResponse(IResponseCode responseCode, String message) {
        this.status = responseCode.getStatus();
        this.message = message;
    }

    private ApiResponse(IResponseCode responseCode, List<FieldError> fieldErrors) {
        this.status = responseCode.getStatus();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.errors = fieldErrors.stream()
                .map(error -> ApiFieldError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
    }

    @Builder
    @Getter
    public static class ApiFieldError {
        private String field;
        private String message;
    }
    
}
