package com.api.TaveShot.domain.Member.editor;

import com.api.TaveShot.domain.Member.domain.Tier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class MemberEditor {
    private final String bojName;
    private final Tier tier;

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String bojName;
        private Tier tier;

        MemberEditorBuilder(){
        }

        public MemberEditor.MemberEditorBuilder bojName(final String bojName) {
            if (StringUtils.hasText(bojName)) {
                this.bojName = bojName;
            }
            return this;
        }

        public MemberEditor.MemberEditorBuilder tier(final Tier tier) {
            if (tier != null) {
                this.tier = tier;
            }
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(bojName, tier);
        }
    }
}
