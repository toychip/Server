package com.api.TaveShot.domain.comment.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.comment.converter.CommentConverter;
import com.api.TaveShot.domain.comment.domain.Comment;
import com.api.TaveShot.domain.comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.comment.dto.request.CommentEditRequest;
import com.api.TaveShot.domain.comment.dto.response.CommentListResponse;
import com.api.TaveShot.domain.comment.dto.response.CommentResponse;
import com.api.TaveShot.domain.comment.editor.CommentEditor;
import com.api.TaveShot.domain.comment.repository.CommentRepository;
import com.api.TaveShot.domain.post.post.PostValidator;
import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.post.domain.PostTier;
import com.api.TaveShot.domain.post.post.repository.PostRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostValidator postValidator;

    /* --------------------------------- CREATE --------------------------------- */
    @Transactional // 데이터 변경하는 메서드에만 명시적으로 적용
    public Long register(final Long postId, final CommentCreateRequest request) {

        Member currentMember = getCurrentMember();

        // request에서 Tier 정보를 받는 것이 아닌, post에서 Tier를 꺼내서 검증
        // member의 티어와 비교하면 됨 validateAuthority 참고

        Post post = getPost(postId);

        validateAuthority(post.getPostTier(), currentMember);

        // ---------------- 부모 댓글 유무 확인 ----------------
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = findParentComment(parentCommentId);

        return createComment(request, currentMember, post, parentCommentOptional);
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    private Post getPost(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));
    }
    private void validateAuthority(final PostTier postTier, final Member currentMember) {
        postValidator.validateAuthority(postTier, currentMember);
    }

    private Optional<Comment> findParentComment(final Long parentCommentId) {
        if (parentCommentId != null) {
            return commentRepository.findById(parentCommentId);
        }
        return Optional.empty();
    }

    private Long createComment(final CommentCreateRequest request, final Member currentMember,
                               final Post post, final Optional<Comment> parentCommentOptional) {
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            return createWithParent(request, currentMember, post, parentComment);
        }

        return createNotParent(request, currentMember, post);
    }

    private Long createWithParent(final CommentCreateRequest request, final Member currentMember,
                                  final Post post, final Comment findParentComment) {
        Comment comment = CommentConverter.createDtoToEntity(request.getComment(), currentMember,
                post, findParentComment);
        commentRepository.save(comment);
        return comment.getId();
    }

    private Long createNotParent(final CommentCreateRequest request, final Member currentMember,
                                 final Post post) {
        Comment comment = CommentConverter.createDtoToEntity(request.getComment(), currentMember, post);
        commentRepository.save(comment);
        return comment.getId();
    }


    /* --------------------------------- READ --------------------------------- */
    public CommentListResponse findComments(final Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponses = CommentConverter.commentsToResponses(comments);
        CommentListResponse response = CommentConverter.toCommentListResponse(commentResponses);
        return response;
    }


    /* --------------------------------- EDIT --------------------------------- */
    @Transactional
    public void edit(Long commentId, CommentEditRequest request) {
        Member currentMember = getCurrentMember();
        Comment comment = getComment(commentId);

        validateCommentWriter(comment, currentMember);

        CommentEditor commentEditor = getCommentEditor(request);

        comment.edit(commentEditor);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorType._COMMENT_NOT_FOUND));
    }

    private void validateCommentWriter(Comment comment, Member currentMember) {
        if (!comment.getMember().getId().equals(currentMember.getId())) {
            throw new ApiException(ErrorType._POST_USER_FORBIDDEN);
        }
    }

    private CommentEditor getCommentEditor(CommentEditRequest request) {
        return CommentEditor.builder()
                .comment(request.getComment())
                .build();
    }


    /* --------------------------------- DELETE --------------------------------- */
    @Transactional
    public void delete(final Long commentId) {
        Member currentMember = getCurrentMember();
        Comment comment = getComment(commentId);

        validateCommentWriter(comment, currentMember);

        commentRepository.delete(comment);
    }


}
