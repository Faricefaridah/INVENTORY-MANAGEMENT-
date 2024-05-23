package com.ims.app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:create"),
    ADMIN_DELETE("admin:create"),
    ADMIN_READ("admin:create"),
    MANAGER_CREATE("admin:create"),
    MANAGER_DELETE("admin:create"),
    MANAGER_READ("admin:create"),
    MANAGER_UPDATE("admin:create"),
    USER_CREATE("admin:create"),
    USER_UPDATE("admin:create"),
    USER_READ("admin:create"),
    USER_DELETE("admin:create"),
    ;
    @Getter
    private final String permission;
}
