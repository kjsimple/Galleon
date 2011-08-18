package com.gydoc.galleon.mybatis;

import com.gydoc.galleon.domain.ExpenseReport;
import org.apache.ibatis.annotations.Insert;

/**
 *
 */
public interface ExpenseReportMapper {
    
    @Insert("INSERT INTO ExpenseReport VALUES(#{id}, #{title}, #{amount}, #{creationDate}, #{version}, #{status}, #{purposeId}, #{userId})")
    void addExpenseReport(ExpenseReport expenseReport);
    
}
