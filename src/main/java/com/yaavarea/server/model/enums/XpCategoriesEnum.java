package com.yaavarea.server.model.enums;

public enum XpCategoriesEnum {
    EVENT_RATING(10), PUBLIC_AUTHORITY_RATING(15), LOCAL_BUSINESS_RATING(10);

    private int xp;
    XpCategoriesEnum(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return this.xp;
    }
}
