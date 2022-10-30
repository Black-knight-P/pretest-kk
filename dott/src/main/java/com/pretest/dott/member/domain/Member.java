package com.pretest.dott.member.domain;


import com.pretest.dott.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "encrypted_email", unique = true, nullable = false)
    private String encryptedEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public Member(String name, String encryptedEmail, String password) {
        this.name = name;
        this.encryptedEmail = encryptedEmail;
        this.password = password;
    }

}
