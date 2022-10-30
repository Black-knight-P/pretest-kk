package com.pretest.dott.member.repository;

import com.pretest.dott.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEncryptedEmail(final String encryptedEmail);
}
