package com.api.TaveShot.domain.Member.domain;

public enum Tier {
    BEGINNER("Beginner"),
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

}
