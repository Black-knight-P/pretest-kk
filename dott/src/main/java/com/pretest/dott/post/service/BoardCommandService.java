package com.pretest.dott.post.service;

import com.pretest.dott.post.domain.Post;
import com.pretest.dott.post.repository.PostJpaRepository;
import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import com.pretest.dott.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BoardCommandService {
    private final PostJpaRepository postJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    public void createBoard(final Long memberId, final String title, final String description) {
        final var memberEntity = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

        final var boardEntity = Post.builder()
                .member(memberEntity)
                .title(title)
                .description(description)
                .build();

        final var savedBoardEntity = postJpaRepository.save(boardEntity);
        log.debug("Saved Board Entity : {}", savedBoardEntity);
    }
}
