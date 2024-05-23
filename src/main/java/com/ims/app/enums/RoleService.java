package com.ims.app.enums;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();
    Role getRoleByName(String roleName);
}
