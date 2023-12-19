package com.api.TaveShot.domain.post.editor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class PostEditor {

    private final String title;
    private final String content;
//    private final MultipartFile attachmentFile;

    public static PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    public static class PostEditorBuilder {
        private String title;
        private String content;

        PostEditorBuilder(){
        }

        public PostEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public PostEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public PostEditor build() {
            return new PostEditor(title, content);
        }
    }

}
