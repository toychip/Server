package com.api.TaveShot.domain.post.post.repository;


import static com.api.TaveShot.domain.post.comment.domain.QComment.comment;
import static com.api.TaveShot.domain.post.image.domain.QImage.image;
import static com.api.TaveShot.domain.post.post.domain.QPost.*;
import static com.api.TaveShot.domain.post.post.domain.QPost.post;
import static com.api.TaveShot.global.constant.OauthConstant.MAX_PAGE_NUMBER;
import static com.api.TaveShot.global.constant.OauthConstant.MAX_PAGE_SIZE;

import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.post.domain.PostTier;
import com.api.TaveShot.domain.post.post.domain.QPost;
import com.api.TaveShot.domain.post.post.dto.request.PostSearchCondition;
import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Post findPostFetchJoin(Long id) {
        Post findPost = jpaQueryFactory
                .select(post)
                .distinct()
                .from(post)
                .leftJoin(post.images, image)
                .leftJoin(post.comments, comment)
                .where(post.id.eq(id))
                .fetchJoin()
                .orderBy(post.id.desc())
                .fetchOne();

        return Optional.ofNullable(findPost)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));
    }

    private List<PostResponse> getSearchPageContent(final PostSearchCondition condition, final Pageable pageable) {
        validatePaging(condition, pageable);

        List<Post> posts = jpaQueryFactory
                .select(post)
                .distinct()
                .from(post)
                .where(
                        judgeTier(condition.getPostTierEnum()),
                        containTitle(condition.getTitle()),
                        containContent(condition.getContent()),
                        containWriter(condition.getWriter())
                )
                .leftJoin(post.images, image)
                .leftJoin(post.comments, comment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<PostResponse> postResponseList = toPostResponses(posts);
        return postResponseList;
    }

    private void validatePaging(PostSearchCondition condition, Pageable pageable) {

        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            throw new ApiException(ErrorType._PAGING_INVALID_PAGE_SIZE);
        }

        if (pageable.getPageSize() > MAX_PAGE_NUMBER) {
            throw new ApiException(ErrorType._PAGING_INVALID_PAGE_NUMBER);
        }

        long totalPosts = safeFetchCount(getSearchPageCount(condition));
        long expectedStartIndex = pageable.getOffset();
        long expectedEndIndex = expectedStartIndex + pageable.getPageSize();

        // 데이터 범위 초과 여부 확인
        if (expectedStartIndex >= totalPosts || expectedEndIndex > totalPosts) {
            throw new ApiException(ErrorType._PAGING_INVALID_DATA_SIZE);
        }
    }

    private long safeFetchCount(JPAQuery<Long> query) {
        Long count = query.fetchOne();
        if (count != null) {
            return count;
        }
        return 0L;
    }

    private BooleanExpression judgeTier(final PostTier postTierEnum) {
        return post.postTier.eq(postTierEnum);
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

    private List<PostResponse> toPostResponses(List<Post> posts) {
        return posts.stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getContent(), p.getWriter(), p.getViewCount(),
                        p.getComments().size(), p.getMemberId(), p.getCreatedDate(), p.getImages()))
                .toList();
    }

    private JPAQuery<Long> getSearchPageCount(final PostSearchCondition condition) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(post)
                .where(
                        judgeTier(condition.getPostTierEnum()),
                        containTitle(condition.getTitle()),
                        containContent(condition.getContent()),
                        containWriter(condition.getWriter())
                );
    }

}
