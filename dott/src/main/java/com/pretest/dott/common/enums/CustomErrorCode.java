package com.pretest.dott.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum CustomErrorCode implements IResponseCode {

    MEMBER_NOT_FOUND(NOT_FOUND.value(), null, "등록된 유저가 없습니다."),
    POST_NOT_FOUND(NOT_FOUND.value(), null, "등록된 게시글이 없습니다."),
    COMMENT_PARENT_NOT_FOUND(NOT_FOUND.value(), null, "등록된 부모 댓글이 없습니다."),
    NOT_MATCHED_POST(BAD_REQUEST.value(), null, "유효하지 않은 댓글 작성 요청입니다."),
    ACCESS_DENIED(UNAUTHORIZED.value(), null, "접근 권한이 없습니다."),
    INCONSISTENCY_PASSWORD(BAD_REQUEST.value(), null, "비밀번호가 동일하지 않습니다."),
    INVALID_FORMAT_PASSWORD(BAD_REQUEST.value(), null, "비밀번호 형식이 틀립니다."),
    PASSWORD_NOT_MATCHED(BAD_REQUEST.value(), null, "비밀번호가 일치하지 않습니다."),
    ALREADY_MEMBER_EXISTS(BAD_REQUEST.value(), null, "동일한 계정이 이미 존재합니다."),
    INVALID_REQUEST_JWT_INFO(UNAUTHORIZED.value(), null, "로그인 인증 정보와 요청 정보가 상이 합니다."),
    ;

    private final int status;
    private final String code;
    private final String message;

}
