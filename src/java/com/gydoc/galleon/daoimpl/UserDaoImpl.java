package com.gydoc.galleon.daoimpl;

import com.gydoc.galleon.dao.UserDao;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.mybatis.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public class UserDaoImpl extends DaoBase implements UserDao {

    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    public void addUser(User u) {
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        userMapper.addUser(u);
    }

    @SuppressWarnings(value={"unchecked"})
    public List<User> findRecentUsers(int maxResults) {
        if (maxResults <= 0) {
            throw new IllegalArgumentException("maxResults should be positive.");
        }
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        return userMapper.findRecentUsers(maxResults);
    }

    public User findUserByLoginId(String login) {
        User user = getSqlSession().getMapper(UserMapper.class).findUserByLogin(login);            
        if (user == null) {
            if (log.isInfoEnabled()) {
                log.warn("found no user against userId: " + login);
            }
            return null;
        }
        return user;
    }

    public List<User> findUsersLikeLogin(String login) {
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        return userMapper.findUsersLikeLogin(login);
    }

}
