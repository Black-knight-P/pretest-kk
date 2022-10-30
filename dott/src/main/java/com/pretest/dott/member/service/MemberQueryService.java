package com.pretest.dott.member.service;

import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import com.pretest.dott.common.utils.AES256Util;
import com.pretest.dott.member.domain.Member;
import com.pretest.dott.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {
    private MemberJpaRepository memberJpaRepository;

    public Member findMemberByEmail(String email) throws GeneralSecurityException {
        final String encryptedEmail = AES256Util.encrypt(email);
        return memberJpaRepository.findByEncryptedEmail(encryptedEmail)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));
    }

}
