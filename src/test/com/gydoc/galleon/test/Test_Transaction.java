package com.gydoc.galleon.test;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.service.UserService;
import org.activiti.engine.IdentityService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 *
 */
@Test
public class Test_Transaction {

    @Test
    public void testTransaction() {
        String id = null;
        try {
            TestTransactionService service = (TestTransactionService) SpringUtil.getBean("testTransaction");
            service.addUser();
            Assert.assertTrue(false, "Exception expected.");
        } catch (Exception e) {
            id = e.getMessage();
        }
        UserService userService = (UserService) SpringUtil.getBean(UserService.S_ID);
        Assert.assertNull(userService.findUserByLoginId("TestTransaction"), "User should not have been created.");
        IdentityService identityService = (IdentityService) SpringUtil.getBean("identityService");
        List result = identityService.createUserQuery().userId(id).list();
        Assert.assertEquals(result.size(), 0, "TestTransaction user should not have been created for Activiti");
    }

}
