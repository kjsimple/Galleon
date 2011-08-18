package com.gydoc.galleon.serviceimpl;

import com.gydoc.galleon.dao.RoleDao;
import com.gydoc.galleon.domain.Role;
import com.gydoc.galleon.service.RoleService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Transactional(propagation= Propagation.SUPPORTS)
public class RoleServiceImpl extends ServiceBase implements RoleService {

    private RoleDao roleDao;

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Role> findRoles() {
        return roleDao.findRoles();
    }

    public Role addRole(Role role) {
        return roleDao.addRole(role);
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

}
