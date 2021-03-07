package com.ihu.Connect_4.enums;

public enum SortingType {
    points("points"),
    wins("wins"),
    loses("loses"),
    draws("draws"),
    games_played("gamesPlayed"),
    wins_vs_ai("winsVsAi");

    private final String field;

    SortingType(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
