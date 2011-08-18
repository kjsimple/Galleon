package com.gydoc.galleon.service;

import com.gydoc.galleon.domain.User;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public interface UserService {

    public static final String S_ID = "userService";

    void addUser(User user);
    User findUserByLoginId(String login);
    boolean authenticate(String login, String password);
    String checkUserForAdd(User user);

    List<User> findRecentUsers(int count);
    List<User> findUsersLikeLogin(String login);
    
}
