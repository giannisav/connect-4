package com.ihu.Connect_4.enums;

public enum SortingOrder {
    asc("asc"),
    desc("desc");

    private final String order;

    SortingOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}