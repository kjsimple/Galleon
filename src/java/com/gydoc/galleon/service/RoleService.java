package com.gydoc.galleon.service;

import com.gydoc.galleon.domain.Role;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public interface RoleService {

    List<Role> findRoles();
    Role addRole(Role role);

}
