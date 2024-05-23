package com.ims.app.enums;

import com.ims.app.enums.Role;
import com.ims.app.enums.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getAllRoles")
    public Set<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{roleName}")
    public Role getRoleByName(@PathVariable String roleName) {
        return roleService.getRoleByName(roleName);
    }


}
