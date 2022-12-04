package com.isl.lionelmaquet.burger2home.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();

    Optional<Role> getSingleRole(Integer roleIdentifier);

    void createRole(Role role);

    void modifyRole(Role role);

    void deleteRole(Integer roleIdentifier);

    Optional<Role> getRoleByUser(Integer userIdentifier);
}
