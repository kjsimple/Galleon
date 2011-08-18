package com.gydoc.galleon.service;

import com.gydoc.galleon.domain.ExpenseReport;

/**
 *
 */
public interface ExpenseReportService {

    public static final String S_ID = "erService";

    void addExpense(ExpenseReport er);

}
