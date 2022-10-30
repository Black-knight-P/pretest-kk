package com.pretest.dott.member.controller;

import com.pretest.dott.common.dto.ApiResponse;
import com.pretest.dott.common.utils.ResponseGenerator;
import com.pretest.dott.member.service.MemberCommandService;
import com.pretest.dott.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;

import static com.pretest.dott.member.dto.MemberCommandDto.Login;
import static com.pretest.dott.member.dto.MemberCommandDto.Register;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerMember(@RequestBody Register request) throws GeneralSecurityException {
        log.debug("registerMember request : {}", request);
        memberCommandService.createMember(request.getEmail(), request.getPassword(), request.getName());
        return ResponseGenerator.makeSuccessResult();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Login.Response>> login(@RequestBody Login.Request request)
            throws GeneralSecurityException {
        log.debug("login request : {}", request);
        final String token = jwtService.createToken(request.getEmail(), request.getPassword());
        final Login.Response response = Login.Response.builder().token(token).build();
        return ResponseGenerator.makeSuccessResult(response);
    }

}
