package com.gydoc.galleon.test;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.dao.UserDao;
import com.gydoc.galleon.domain.User;
import com.gydoc.galleon.service.UserService;
import org.activiti.engine.IdentityService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 *
 */
@Transactional(propagation= Propagation.SUPPORTS)
public class TestTransactionServiceImpl implements TestTransactionService {

    private Long id = null;
    private UserDao userDao;

    @Transactional(propagation=Propagation.REQUIRED)
    public void addUser() {
        User user = new User();
        user.setPassword("t");
        user.setUserLogin("TestTransaction");
        user.setUserName("Test Transaction");
        userDao.addUser(user);

        id = user.getId();
        System.out.println("id = " + id);
        IdentityService identityService = (IdentityService) SpringUtil.getBean("identityService");
        identityService.saveUser(identityService.newUser(user.getId().toString()));
        throw new RuntimeException(id.toString());
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
