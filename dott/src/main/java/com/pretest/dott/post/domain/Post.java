package com.pretest.dott.post.domain;

import com.pretest.dott.common.entity.BaseTimeEntity;
import com.pretest.dott.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    private String title;

    private String description;

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public Post(final Member member, final String title, final String description) {
        this.member = member;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof Post)) { return false; }
        Post post = (Post) obj;
        return Objects.equals(post.getId(), id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
