package com.api.TaveShot.domain.post.post.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import com.api.TaveShot.domain.post.comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.post.comment.service.CommentService;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CommentDataInit {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("팀원들 계정으로 예시 댓글 생성")
    @Transactional
    @Rollback(false)
    void initExPost() throws IOException {

        Member toychip = getMember("toychip");
//        Member young0519 = getMember("young0519");
//        Member cucubob = getMember("cucubob");
//        Member thwjddlqslek = getMember("thwjddlqslek");
//        Member wjd4204 = getMember("wjd4204");

        registerData(toychip);
//        registerData(young0519, multipartFiles);
//        registerData(cucubob, multipartFiles);
//        registerData(thwjddlqslek, multipartFiles);
//        registerData(wjd4204, multipartFiles);
    }

    private Member getMember(String gitLoginId) {
        return memberRepository.findByGitLoginId(gitLoginId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

    private void registerData(Member member) {
        for (int i = 0; i < 300; i++) {
            Long postId = getPostId((long) i);
            Long parentCommentId = getParentCommentId(i);
            String gitLoginId = member.getGitLoginId();

            CommentCreateRequest req = CommentCreateRequest.builder()
                    .comment(gitLoginId + " Test Ex Data Comment " + i)
                    .parentCommentId(parentCommentId)
                    .build();

            Long newCommentId = commentService.registerTest(postId, req, member);
            System.out.println("newCommentId = " + newCommentId);
        }
    }

    private Long getPostId(Long i) {
        long temp = i / 100;
        if (temp < 1) {
            return 1L;
        }
        return temp;
    }

    private Long getParentCommentId(int i) {
        if (i > 2 && i % 2 == 0) {
            return (long) i - 2;
        }
        return null;
    }
}
