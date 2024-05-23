package com.ims.app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ims.app.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_READ
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_DELETE,
                    MANAGER_READ,
                    MANAGER_UPDATE)
    ),
    USER(
            Set.of(USER_CREATE,
                    USER_UPDATE,
                    USER_READ,
                    USER_DELETE)
    );


    private final Set<Permission> permissions;


    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return authorities;
    }


}



