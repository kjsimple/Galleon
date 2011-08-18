package com.gydoc.galleon.daoimpl;

import com.gydoc.galleon.dao.ExpenseReportDao;
import com.gydoc.galleon.domain.ExpenseReport;
import com.gydoc.galleon.mybatis.ExpenseReportMapper;

/**
 *
 */
public class ExpenseReportDaoImpl extends DaoBase implements ExpenseReportDao {

    public ExpenseReportDaoImpl() {
        
    }

    public void addExpenseReport(ExpenseReport er) {
        ExpenseReportMapper mapper = getSqlSession().getMapper(ExpenseReportMapper.class);
        mapper.addExpenseReport(er);
    }
    
}
