package com.yaavarea.server.model.enums;

public enum RoadEnum implements EnumInterface{
    QUALITY("quality"), TRAFFIC("traffic"), SAFETY("safety"), HYGIENE("hygiene");

    public final String label;
    RoadEnum(String type) {
        this.label = type;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
