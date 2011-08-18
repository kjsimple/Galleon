package com.gydoc.galleon.dao;

import com.gydoc.galleon.domain.User;
import java.util.List;

/**
 *
 */
public interface UserDao {
    
    void addUser(User u);

    List<User> findRecentUsers(int count);

    User findUserByLoginId(String login);

    List<User> findUsersLikeLogin(String login);
    
}
