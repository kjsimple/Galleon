package com.gydoc.galleon.dao;

import com.gydoc.galleon.domain.Role;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public interface RoleDao {

    List<Role> findRoles();
    Role addRole(Role role);

}
