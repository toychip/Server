package com.api.TaveShot.domain.Member.domain;

public enum Tier {
    Beginner("Beginner"),
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    PLATINUM("Platinum"),
    DIAMOND("Diamond"),
    RUBY("Ruby"),
    MASTER("Master");

    private final String tier;

    Tier(String tier) {
        this.tier = tier;
    }

    public String getTier() {
        return tier;
    }

    // 문자열을 enum으로 변환하는 메서드 (옵션)
    public static Tier fromString(String text) {
        for (Tier t : Tier.values()) {
            if (t.tier.equalsIgnoreCase(text)) {
                return t;
            }
        }
        return null; // 또는 예외 처리
    }
}
