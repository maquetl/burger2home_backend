package com.isl.lionelmaquet.burger2home.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();

    Optional<Role> getSingleRole(Integer roleIdentifier);

    Role createRole(Role role);

    Role modifyRole(Role role);

    Optional<Role> getRoleByUser(Integer userIdentifier);
}
