package com.pretest.dott.security.context;

import com.pretest.dott.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class MemberContext extends User implements Serializable {
    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEncryptedEmail(), member.getPassword(), authorities);
    }
}
