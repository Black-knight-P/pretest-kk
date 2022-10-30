package com.pretest.dott.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretest.dott.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pretest.dott.common.enums.CustomErrorCode.INVALID_REQUEST_JWT_INFO;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.debug("AuthenticationEntryPoint : {}", authException.getMessage());
        response.setStatus(UNAUTHORIZED.value());
        final var responseJsonString = objectMapper.writeValueAsString(ApiResponse.of(INVALID_REQUEST_JWT_INFO));
        response.getWriter().print(responseJsonString);
    }
}
