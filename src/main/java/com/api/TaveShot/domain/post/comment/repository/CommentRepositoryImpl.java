package com.api.TaveShot.domain.post.comment.repository;

import static com.api.TaveShot.domain.post.comment.domain.QComment.comment;

import com.api.TaveShot.domain.post.comment.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findByPostId(Long postId) {
        return jpaQueryFactory
                .selectFrom(comment)
                .leftJoin(comment.child)
                .fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.asc()
                )
                .fetch();
    }
}
