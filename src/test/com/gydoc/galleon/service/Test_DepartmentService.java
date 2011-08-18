package com.gydoc.galleon.service;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.Department;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
@Test
public class Test_DepartmentService {
    
    @Test
    public void testAddDepartment() {
        Department department = new Department();
        department.setCode("R&D");
        department.setName("Development");
        department.setDescription("development and research.");
                
        DepartmentService service = (DepartmentService) SpringUtil.getBean(DepartmentService.S_ID);
        service.addDepartment(department);
        Assert.assertNotNull(department.getId(), "Failed to add Department.");
    }
    
}
