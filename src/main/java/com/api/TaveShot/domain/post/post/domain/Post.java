package com.api.TaveShot.domain.post.post.domain;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.post.image.domain.Image;
import com.api.TaveShot.domain.post.post.editor.PostEditor;
import com.api.TaveShot.domain.base.BaseEntity;
import com.api.TaveShot.domain.post.post.editor.PostEditor.PostEditorBuilder;
import com.api.TaveShot.global.util.TimeUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    private PostTier postTier;

    @Builder.Default
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Column(nullable = false)
    private String writer;

    // default = 0 설정
    @Builder.Default
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Comment> comments;

    public PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final PostEditor postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();
    }

    public Long getMemberId() {
        return member.getId();
    }

    public String getCreatedTime() {
        LocalDateTime createdDate = getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    public void addCount() {
        viewCount++;
    }

}