package com.AdoNoColor.domain.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.CATS_READ, Permission.OWNERS_READ, Permission.CATS_WRITE)),
    ADMIN(Set.of(Permission.CATS_WRITE, Permission.CATS_READ, Permission.OWNERS_WRITE, Permission.OWNERS_READ));

    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
