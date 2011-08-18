package com.gydoc.galleon.mybatis;

import com.gydoc.galleon.domain.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 */
public interface RoleMapper {

    @Insert("INSERT INTO Roles VALUES(#{id}, #{name}, #{description}, #{creationDate})")
    void addRole(Role role);
    
    @Select("SELECT * FROM Roles")
    List<Role> findRoles();

}
