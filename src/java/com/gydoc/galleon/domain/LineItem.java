package com.gydoc.galleon.domain;

/**
 *
 */
public class LineItem extends BizObject {
    
    private int amount;
    private String description;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
