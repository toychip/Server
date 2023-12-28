package com.api.TaveShot.domain.Member.domain;

public enum Tier {
    BEGINNER("Beginner", 0, 0),
    BRONZE("Bronze", 1, 5),
    SILVER("Silver", 6, 10),
    GOLD("Gold", 11, 15),
    PLATINUM("Platinum", 16, 20),
    DIAMOND("Diamond", 21, 25),
    RUBY("Ruby", 26, 30),
    MASTER("Master", 31, 31);

    private final String tier;
    private final int minBojTier;
    private final int maxBojTier;

    Tier(String tier, int minBojTier, int maxBojTier) {
        this.tier = tier;
        this.minBojTier = minBojTier;
        this.maxBojTier = maxBojTier;
    }

    public String getTier() {
        return tier;
    }

    public static Tier fromBojTier(int bojTier) {
        for (Tier tier : Tier.values()) {
            if (bojTier >= tier.minBojTier && bojTier <= tier.maxBojTier) {
                return tier;
            }
        }
        return BEGINNER;
    }
}
