package com.api.TaveShot.domain.comment.domain;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.base.BaseEntity;
import com.api.TaveShot.domain.comment.editor.CommentEditor;
import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.global.util.TimeUtil;
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
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> child = new ArrayList<>(); // 자식 댓글들

    public CommentEditor.CommentEditorBuilder toEditor(){
        return CommentEditor.builder()
                .comment(content);
    }

    public void edit(final CommentEditor commentEditor) {
        this.content = commentEditor.getContent();
    }

    public String getCreatedTime() {
        LocalDateTime createdDate = getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }


}
