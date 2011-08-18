package com.gydoc.galleon.mybatis;

import com.gydoc.galleon.domain.Department;
import org.apache.ibatis.annotations.Insert;

/**
 *
 */
public interface DepartmentMapper {
    
    @Insert("INSERT INTO Department VALUES(#{id}, NULL, #{name}, #{code}, #{description}, #{managerId}, #{version}, #{creationDate})")
    void addDepartment(Department department);
    
    @Insert("INSERT INTO Department_User(departmentid, userid) VALUES(#{0}, #{1})")
    void addUserToDepartment(Long departmentId, Long userId);
    
}
