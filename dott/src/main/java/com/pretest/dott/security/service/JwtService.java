package com.pretest.dott.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import com.pretest.dott.common.utils.AES256Util;
import com.pretest.dott.member.domain.Member;
import com.pretest.dott.member.repository.MemberJpaRepository;
import com.pretest.dott.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class JwtService {
    private final MemberJpaRepository memberJpaRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final String JWT_BLACKLIST_KEY = "DOTT:JWT:BL:";

    private void isValidMember(final Member member, final String password) {
        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCHED);
    }

    public String createToken(final String email, final String password) throws GeneralSecurityException {
        final var encryptedEmail = AES256Util.encrypt(email);
        final var memberEntity = memberJpaRepository.findByEncryptedEmail(encryptedEmail)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));
        log.debug("Member Info : {}", memberEntity);
        isValidMember(memberEntity, password);
        return jwtTokenProvider.createToken(memberEntity.getEncryptedEmail());
    }

    public void logout(HttpServletRequest request) throws JsonProcessingException {
//        String token = jwtTokenProvider.resolveToken(request);
//        redisService.setStringValue(JWT_BLACKLIST_KEY + token, "LOGOUT JWT VALUE", Duration.ofHours(1));
    }


}
