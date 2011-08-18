package com.gydoc.galleon.serviceimpl;

import com.gydoc.galleon.dao.ExpenseReportDao;
import com.gydoc.galleon.domain.ExpenseReport;
import com.gydoc.galleon.service.ExpenseReportService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional(propagation= Propagation.SUPPORTS)
public class ExpenseReportServiceImpl implements ExpenseReportService {

    private ExpenseReportDao expenseReportDao;

    @Transactional(propagation=Propagation.REQUIRED)
    public void addExpense(ExpenseReport er) {
        expenseReportDao.addExpenseReport(er);
    }

    public void setExpenseReportDao(ExpenseReportDao erDao) {
        this.expenseReportDao = erDao;
    }
    
}
