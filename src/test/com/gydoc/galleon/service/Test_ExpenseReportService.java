package com.gydoc.galleon.service;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.ExpenseReport;
import com.gydoc.galleon.domain.User;
import org.testng.annotations.Test;

/**
 *
 */
@Test
public class Test_ExpenseReportService {

    @Test
    public void testAdd() {
        ExpenseReportService ers = (ExpenseReportService) SpringUtil.getBean(ExpenseReportService.S_ID);
        UserService us = (UserService) SpringUtil.getBean(UserService.S_ID);
        ExpenseReport er = new ExpenseReport();
        er.setOwner(us.findUserByLoginId("user001"));
        er.setAmount(106);
        er.setTitle("Test add expense report.");
        ers.addExpense(er);
    }

}
