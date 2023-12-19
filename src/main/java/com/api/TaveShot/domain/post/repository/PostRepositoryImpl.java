package com.api.TaveShot.domain.post.repository;


import static com.api.TaveShot.domain.post.domain.QPost.post;

import com.api.TaveShot.domain.post.dto.response.PostResponse;
import com.api.TaveShot.domain.post.dto.request.PostSearchCondition;
import com.api.TaveShot.domain.post.dto.response.QPostResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostResponse> searchPagePost(final PostSearchCondition condition, final Pageable pageable) {

        List<PostResponse> postResponses = getSearchPageContent(condition, pageable);
        JPAQuery<Long> searchPageCount = getSearchPageCount(condition);

        return PageableExecutionUtils.getPage(postResponses, pageable, searchPageCount::fetchOne);
    }

    private List<PostResponse> getSearchPageContent(final PostSearchCondition condition, final Pageable pageable) {
        return jpaQueryFactory
                .select(
                        new QPostResponse(post.id, post.title, post.content,
                                post.writer, post.viewCount, post.member.id))
                .from(post)
                .where(
                        containTitle(condition.getTitle()),
                        containContent(condition.getContent()),
                        containWriter(condition.getWriter())
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private JPAQuery<Long> getSearchPageCount(final PostSearchCondition condition) {
         return jpaQueryFactory
                .select(Wildcard.count)
                .from(post)
                .where(
                        containTitle(condition.getTitle()),
                        containContent(condition.getContent()),
                        containWriter(condition.getWriter())
                );
    }

    private BooleanExpression containTitle(final String title) {
        if (StringUtils.hasText(title)) {
            return post.title.contains(title);
        }
        return null;
    }

    private BooleanExpression containContent(final String content) {
        if (StringUtils.hasText(content)) {
            return post.content.contains(content);
        }
        return null;
    }

    private BooleanExpression containWriter(final String writer) {
        if (StringUtils.hasText(writer)) {
            return post.writer.contains(writer);
        }
        return null;
    }

}
