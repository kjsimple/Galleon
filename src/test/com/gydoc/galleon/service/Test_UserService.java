package com.gydoc.galleon.service;

import com.gydoc.galleon.Constant;
import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.User;
import org.activiti.engine.IdentityService;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

/**
 * 
 */
@Test
public class Test_UserService {

    @Test
    public void testAuthenticateSuccess() {
        UserService userService = (UserService) SpringUtil.getBean(UserService.S_ID);
        assertTrue("Authenticate failed against right password.", userService.authenticate("admin", "admin"));
    }

    @Test
    public void testAuthenticateFailed() {
        UserService userService = (UserService) SpringUtil.getBean(UserService.S_ID);
        assertFalse("Authenticate succeed against wrong password.", userService.authenticate("admin", "wrongpass"));
    }

    @Test
    public void testAddAndFindByLogin() {
        UserService userService = (UserService) SpringUtil.getBean(UserService.S_ID);
        User user = new User();
        String password = "test001";
        String userName = "Test 0000001";
        String userLogin = "Test0000001";
        user.setPassword(password);
        user.setUserName(userName);
        user.setUserLogin(userLogin);
        userService.addUser(user);

        User u = userService.findUserByLoginId("Test0000001");
        assertEquals("userLogin does not match.", u.getUserLogin(), userLogin);
        assertEquals("userName dose not match.", u.getUserName(), userName);
        assertFalse("password is not encrypted.", u.getPassword().equals(password));
        assertTrue("Could not login with added user.", userService.authenticate(userLogin, password));
        IdentityService identityService = (IdentityService) SpringUtil.getBean("identityService");
        org.activiti.engine.identity.User actUser = identityService.createUserQuery().userId(u.getId().toString()).singleResult();
        assertNotNull("Activiti user should not be null.", actUser);
        
        u = userService.findUserByLoginId("Non-Exists");
        assertNull("User should be null.", u);
    }

    @Test
    public void testCheckUserForAdd() {
        UserService userService = (UserService) SpringUtil.getBean(UserService.S_ID);
        User u = new User();
        assertEquals("Validation failed against empty user login.", Constant.USER_LOGIN_EMPTY, userService.checkUserForAdd(u));
        u.setUserLogin("Test007");
        assertEquals("Validation failed against empty user name.", Constant.USER_USERNAME_EMPTY, userService.checkUserForAdd(u));
        u.setUserName("Blue Marry");
        assertEquals("Validation failed against empty user password.", Constant.ERROR_00U000001, userService.checkUserForAdd(u));
        u.setPassword("pass");
        assertEquals("Validation should be OK.", Constant.BOCHECK_OK, userService.checkUserForAdd(u));
    }

}
