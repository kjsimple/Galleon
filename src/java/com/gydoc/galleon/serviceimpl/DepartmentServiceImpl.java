package com.gydoc.galleon.serviceimpl;

import com.gydoc.galleon.dao.DepartmentDao;
import com.gydoc.galleon.domain.Department;
import com.gydoc.galleon.service.DepartmentService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional(propagation= Propagation.SUPPORTS)
public class DepartmentServiceImpl extends ServiceBase implements DepartmentService {

    private DepartmentDao depDao;

    @Transactional(propagation=Propagation.REQUIRED)
    public void addDepartment(Department dep) {
        depDao.addDepartment(dep);
    }

    public void setDepartmentDao(DepartmentDao depDao) {
        this.depDao = depDao;
    }

}
