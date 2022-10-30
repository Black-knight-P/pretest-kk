package com.pretest.dott.member.service;

import com.pretest.dott.common.utils.AES256Util;
import com.pretest.dott.member.domain.Member;
import com.pretest.dott.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandService {
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public void createMember(final String email, final String password, final String name)
            throws GeneralSecurityException {
        final String encryptedEmail = AES256Util.encrypt(email);
        final String encodedPassword = passwordEncoder.encode(password);

        final Member member = Member.builder()
                .name(name)
                .encryptedEmail(encryptedEmail)
                .password(encodedPassword)
                .build();

        final Member savedEntity = memberJpaRepository.save(member);
        log.debug("create member : {} ", savedEntity);
    }

}
