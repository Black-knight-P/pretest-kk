package com.pretest.dott.post.service;

import com.pretest.dott.post.domain.Comment;
import com.pretest.dott.post.domain.Post;
import com.pretest.dott.post.repository.CommentJpaRepository;
import com.pretest.dott.post.repository.PostJpaRepository;
import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import com.pretest.dott.member.domain.Member;
import com.pretest.dott.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentCommandService {
    private final CommentJpaRepository commentJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public void createComment(final String encryptedMemberEmail, final Long postId,
                              final Long parentId, final String content) {
        final Member member = memberJpaRepository.findByEncryptedEmail(encryptedMemberEmail)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));
        final Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));
        final Comment parent = nonNull(parentId)
                ? commentJpaRepository.findById(parentId).orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_PARENT_NOT_FOUND))
                : null;

        validate(post, parent);

        final Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .parent(parent)
                .content(content)
                .build();
        final var savedEntity = commentJpaRepository.save(comment);
        log.debug("create comment saved entity : {} ", savedEntity);
    }

    private void validate(final Post post, final Comment parent) {
        if (nonNull(parent)) {
            if (post.equals(parent.getPost())) {
                throw new CustomException(CustomErrorCode.MEMBER_NOT_FOUND);
            }
        }
    }

}
