package com.api.TaveShot.domain.Post.domain;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Transient
    private MultipartFile attachmentFile;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String gitLoginId;

    @Transient
    private Long commentCount; //DB에 저장된 댓글 수를 조회해 설정


    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    // gitLoginId 설정 메서드
    public void setGitLoginId(String gitLoginId) {
        this.gitLoginId = gitLoginId;
    }

    /* 게시글 수정 */
    public void update(String title, String content, MultipartFile attachmentFile) {
        this.title = title;
        this.content = content;
        this.attachmentFile = attachmentFile;
    }


}