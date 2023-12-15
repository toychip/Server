package com.api.TaveShot.domain.Post.domain;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.List;

import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    /**
     * image uri 로 변경
     */
//    @Transient
//    private MultipartFile attachmentFile;

    @Column(nullable = false)
    private String writer;

    // default = 0 설정
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

//    /* 게시글 수정 */
//    public void update(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    public Long getMemberId() {
        return member.getId();
    }

}