package com.api.TaveShot.domain.Post.repository;


import static com.api.TaveShot.domain.Post.domain.QPost.post;

import com.api.TaveShot.domain.Post.dto.PostResponse;
import com.api.TaveShot.domain.Post.dto.PostSearchCondition;
import com.api.TaveShot.domain.Post.dto.QPostResponse;
import com.api.TaveShot.global.config.DataBaseConfig;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final DataBaseConfig dataBaseConfig;

    @Override
    public List<PostResponse> searchPagePost(PostSearchCondition condition, Pageable pageable) {

        return dataBaseConfig.jpaQueryFactory()
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

    private BooleanExpression containTitle(String title) {
        if (StringUtils.hasText(title)) {
            return post.title.contains(title);
        }
        return null;
    }

    private BooleanExpression containContent(String content) {
        if (StringUtils.hasText(content)) {
            return post.content.contains(content);
        }
        return null;
    }

    private BooleanExpression containWriter(String writer) {
        if (StringUtils.hasText(writer)) {
            return post.writer.contains(writer);
        }
        return null;
    }

}
