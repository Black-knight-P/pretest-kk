package com.pretest.dott.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NormalResponseCode implements IResponseCode {
    SUCCESS(200, null,"SUCCESS"),
    NO_RESULT(200, null, "NO_RESULT"),
    BAD_REQUEST(400, null,"BAD_REQUEST"),
    UNAUTHORIZED(401, null,"UNAUTHORIZED"),
    ACCESS_DENIED(403, null, "ACCESS_DENIED"),
    NOT_FOUND(404, null,"NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, null,"INTERNAL_SERVER_ERROR");

    private final int status;
    private final String code;
    private final String message;
}
