package com.pretest.dott.security;

import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException, UsernameNotFoundException, CustomException {
        final var token = jwtTokenProvider.resolveToken((HttpServletRequest) request); // 헤더에서 JWT 가져옴
        validateToken(token);
        final var authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext 에 Authentication 저장
        chain.doFilter(request, response);
    }

    private void validateToken(final String token) throws CustomException{
        if (Objects.isNull(token)) {
            log.debug("token is null");
            throw new CustomException(CustomErrorCode.INVALID_REQUEST_JWT_INFO);
        }
        if (jwtTokenProvider.isValidateToken(token)) {
            log.debug("expired token info");
            throw new CustomException(CustomErrorCode.INVALID_REQUEST_JWT_INFO);
        }
        if (isLogout(token)) {
            log.debug("already logout token");
            throw new CustomException(CustomErrorCode.INVALID_REQUEST_JWT_INFO);
        }
    }

    private boolean isLogout(String token) {
//        String value = redisService.getStringValue("PAYZ:SHOP:JWT:BL:" + token);
//        return value != null;
        return false;
    }
}
