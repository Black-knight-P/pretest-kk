package com.pretest.dott.common.utils;

import com.pretest.dott.common.dto.ApiResponse;
import com.pretest.dott.common.enums.IResponseCode;
import com.pretest.dott.common.enums.NormalResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;

public class ResponseGenerator {
    public static ResponseEntity<ApiResponse<?>> makeSuccessResult() {
        return ResponseEntity.ok(ApiResponse.of(NormalResponseCode.SUCCESS));
    }

    public static <T> ResponseEntity<ApiResponse<T>> makeSuccessResult(T data) {
        return ResponseEntity.ok(ApiResponse.of(NormalResponseCode.SUCCESS, data));
    }

    public static ResponseEntity<ApiResponse<?>> makeBadRequestResult() {
        return ResponseEntity.status(NormalResponseCode.BAD_REQUEST.getStatus()).body(ApiResponse.of(NormalResponseCode.BAD_REQUEST));
    }

    public static ResponseEntity<ApiResponse<?>> makeBadRequestResult(List<FieldError> errors) {
        return ResponseEntity.status(NormalResponseCode.BAD_REQUEST.getStatus()).body(ApiResponse.of(NormalResponseCode.BAD_REQUEST, errors));
    }

    public static ResponseEntity<ApiResponse<?>> makeFailResult(IResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getStatus()).body(ApiResponse.of(responseCode));
    }

    public static ResponseEntity<ApiResponse<?>> makeResultWithMessage(IResponseCode responseCode, String message) {
        return ResponseEntity.status(responseCode.getStatus()).body(ApiResponse.of(responseCode, message));
    }
}
