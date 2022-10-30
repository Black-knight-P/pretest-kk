package com.pretest.dott.security;

import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import com.pretest.dott.security.constant.JwtSecret;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private static final long TOKEN_VALID_TIME = 30 * 60 * 1000L; // 토큰 유효시간 30분

    private final UserDetailsService userDetailsService;

    // JWT 토큰 생성
    public String createToken(String email) {
        var claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
        final var now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, JwtSecret.getSecretKey())  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 만료된 토큰 생성 (테스트용)
    public String createExpiredToken(String shopAccountEmail, List<String> roles) {
        final var claims = Jwts.claims().setSubject(shopAccountEmail); // JWT payload 에 저장되는 정보단위
        final var now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime())) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, JwtSecret.getSecretKey())  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) throws UsernameNotFoundException {
        final var userDetails = userDetailsService.loadUserByUsername(this.getShopAccountEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getShopAccountEmail(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(JwtSecret.getSecretKey())
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "Bearer "+ {jwt}
    public String resolveToken(HttpServletRequest request) {
        final var authorization = request.getHeader("Authorization");
        if (authorization == null) return null;
        return authorization.substring("Bearer ".length());
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean isValidateToken(String jwtToken) {
        try {
            var claimsJws = Jwts.parser().setSigningKey(JwtSecret.getSecretKey()).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.debug("isValidateToken Exception : {} ", e.getMessage());
            throw new CustomException(CustomErrorCode.INVALID_REQUEST_JWT_INFO);
        }
    }
}

