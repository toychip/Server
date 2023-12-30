package com.api.TaveShot.domain.comment.repository;

import static com.api.TaveShot.domain.comment.domain.QComment.comment;

import com.api.TaveShot.domain.comment.domain.Comment;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Comment> findByPostId(Long postId, Pageable pageable) {

        List<Comment> content = findByPostIdContent(postId, pageable);
        JPAQuery<Long> count = findByPostIdCount(postId);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    public List<Comment> findByPostIdContent(Long postId, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(comment)
                .leftJoin(comment.child)
                .fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.asc()
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    public JPAQuery<Long> findByPostIdCount(Long postId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(comment)
                .leftJoin(comment.child)
                .fetchJoin()
                .where(comment.post.id.eq(postId));
    }
}
