package com.AdoNoColor.domain.entity;

public enum Permission {
    CATS_READ("cats:read"),
    CATS_WRITE("cats:write"),
    OWNERS_READ("owners:read"),
    OWNERS_WRITE("owners:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
