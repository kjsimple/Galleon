package com.gydoc.galleon.daoimpl;

import com.gydoc.galleon.dao.RoleDao;
import com.gydoc.galleon.domain.Role;
import com.gydoc.galleon.mybatis.RoleMapper;

import java.util.List;

/**
 *
 */
public class RoleDaoImpl extends DaoBase implements RoleDao {

    @SuppressWarnings("unchecked")
    public List<Role> findRoles() {
        return getSqlSession().getMapper(RoleMapper.class).findRoles();
    }

    public Role addRole(Role role) {
        RoleMapper mapper = getSqlSession().getMapper(RoleMapper.class);
        mapper.addRole(role);
        return role;
    }

}
