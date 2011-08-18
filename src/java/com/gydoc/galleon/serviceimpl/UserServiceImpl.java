package com.gydoc.galleon.serviceimpl;

import com.gydoc.galleon.Constant;
import com.gydoc.galleon.GalleonException;
import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.dao.UserDao;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.service.UserService;
import org.activiti.engine.IdentityService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.List;

/**
 *
 */
@Transactional(propagation=Propagation.SUPPORTS)
public class UserServiceImpl extends ServiceBase implements UserService {

    private UserDao userDao;
    private String sha1SaltStart = "godisagirl";
    private String sha1SaltEnd = "yfct";

    private String encryptPassword(String password) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update((sha1SaltStart + password + sha1SaltEnd).getBytes("UTF-8"));
            byte[] bytes = digest.digest();
            for (byte b : bytes) {
                result.append(String.format("%1$02X", b));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not encrypt user password.");
        }
        return result.toString();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void addUser(User user) {
        String checkResult = checkUserForAdd(user);
        if (!Constant.BOCHECK_OK.equals(checkUserForAdd(user))) {
            throw new GalleonException(checkResult);
        }
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.addUser(user);
        IdentityService identityService = (IdentityService) SpringUtil.getBean("identityService");
        identityService.saveUser(identityService.newUser(user.getId().toString()));
    }

    public User findUserByLoginId(String login) {
        return userDao.findUserByLoginId(login);
    }

    public boolean authenticate(String login, String password) {
        User user = findUserByLoginId(login);
        return user != null && user.getPassword().equals(encryptPassword(password));
    }

    public String checkUserForAdd(User user) {
        if (user.getUserLogin() == null || user.getUserLogin().equals("")) {
            return Constant.USER_LOGIN_EMPTY;
        }
        if (user.getUserName() == null || user.getUserName().equals("")) {
            return Constant.USER_USERNAME_EMPTY;
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            return Constant.ERROR_00U000001;
        }
        return Constant.BOCHECK_OK;
    }

    public List<User> findRecentUsers(int count) {
        return userDao.findRecentUsers(count);
    }

    public List<User> findUsersLikeLogin(String login) {
        return userDao.findUsersLikeLogin(login);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public static void main(String[] args) {
        UserServiceImpl usi = new UserServiceImpl();
        System.out.println(usi.encryptPassword("admin"));
        System.out.println(usi.encryptPassword("admin2"));
        System.out.println(usi.encryptPassword("abc"));
        System.out.println(usi.encryptPassword("abciewoiew"));
        System.out.println(usi.encryptPassword("0abciewoiew2132"));
        System.out.println(usi.encryptPassword("pass"));
    }
    
}
