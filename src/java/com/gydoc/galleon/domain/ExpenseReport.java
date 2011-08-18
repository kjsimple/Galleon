package com.gydoc.galleon.domain;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ExpenseReport extends BizObject {

    private String title;
    private double amount;
    private Purpose purpose;
    private User owner;
    private Set<LineItem> lines = new HashSet<LineItem>();
    private int status = -1;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /*
    public Set<LineItem> getLines() {
        return lines;
    }

    public void setLines(Set<LineItem> lines) {
        this.lines = lines;
    }
    */

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public Long getPurposeId() {
        if (getPurpose() != null) {
            return getPurpose().getId();
        }
        return null;
    }
    
    public Long getUserId() {
        if (getOwner() != null) {
            return getOwner().getId();
        }
        return null;
    }
    
}
