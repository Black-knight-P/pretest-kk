package com.pretest.dott.security.service;

import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import com.pretest.dott.common.utils.AES256Util;
import com.pretest.dott.member.repository.MemberJpaRepository;
import com.pretest.dott.security.context.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            final var encryptedEmail = AES256Util.encrypt(email);
            final var memberEntity = memberJpaRepository.findByEncryptedEmail(encryptedEmail)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));
            return new MemberContext(memberEntity, null);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
