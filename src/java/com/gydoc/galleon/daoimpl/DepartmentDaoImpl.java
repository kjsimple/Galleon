package com.gydoc.galleon.daoimpl;

import com.gydoc.galleon.dao.DepartmentDao;
import com.gydoc.galleon.domain.Department;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.mybatis.DepartmentMapper;
import com.gydoc.galleon.mybatis.UserMapper;
import com.gydoc.galleon.daoimpl.DaoBase;

/**
 *
 */
public class DepartmentDaoImpl extends DaoBase implements DepartmentDao {

    public void addDepartment(Department dep) {
        DepartmentMapper departmentMapper = getSqlSession().getMapper(DepartmentMapper.class);
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        
        departmentMapper.addDepartment(dep);
        
        for (User u : dep.getUsers()) {
            departmentMapper.addUserToDepartment(dep.getId(), u.getId());
            userMapper.updateVersion(u);
        }
    }

}
