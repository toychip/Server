package com.api.TaveShot.domain.comment.editor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class CommentEditor {

    private final String comment;

    public static CommentEditorBuilder builder() {
        return new CommentEditorBuilder();
    }

    public static class CommentEditorBuilder {
        private String comment;

        CommentEditorBuilder() {
        }

        public CommentEditorBuilder comment(final String comment) {
            if (StringUtils.hasText(comment)) {
                this.comment = comment;
            }
            return this;
        }

        public CommentEditor build() {
            return new CommentEditor(this.comment);
        }
    }
}