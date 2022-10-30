package com.pretest.dott.post.domain;

import com.pretest.dott.common.entity.BaseTimeEntity;
import com.pretest.dott.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    private String content;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private List<Comment> children = new ArrayList<>();

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public Comment (final Member member, final Post post, final Comment parent, final String content) {
        this.member = member;
        this.post = post;
        this.parent = parent;
        this.content = content;
    }

}
