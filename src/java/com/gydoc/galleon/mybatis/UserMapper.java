package com.gydoc.galleon.mybatis;

import com.gydoc.galleon.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *
 */
public interface UserMapper {
    
    @Insert("INSERT INTO Users VALUES(#{id}, #{userLogin}, #{userName}, #{password}, #{creationDate}, #{version})")
    void addUser(User user);

    @Select("SELECT * FROM Users ORDER BY creationDate DESC")
    List<User> findRecentUsers(int maxSize);
    
    User findUserByLogin(String userLogin);

    List<User> findUsersLikeLogin(String login);

    @Update("UPDATE Users SET version=version+1 WHERE id=#{id} AND version=#{version}")
    int updateVersion(User u);
    
}
