package com.gydoc.galleon.mybatis;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.ExpenseReport;
import com.gydoc.galleon.service.ExpenseReportService;
import com.gydoc.galleon.service.UserService;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
@Test
public class Test_MybatisInterceptor {

    @Test
    public void testInterceptor() {
        ExpenseReportService ers = (ExpenseReportService) SpringUtil.getBean(ExpenseReportService.S_ID);
        UserService us = (UserService) SpringUtil.getBean(UserService.S_ID);
        ExpenseReport er = new ExpenseReport();
        er.setOwner(us.findUserByLoginId("user001"));
        er.setAmount(1063);
        er.setTitle("Test Mybatis interceptor.");
        ers.addExpense(er);

        Assert.assertNotNull(er.getCreationDate());
        Assert.assertEquals(0L, er.getVersion().longValue(), "New object's version should be 0.");
    }

}
