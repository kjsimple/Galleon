package com.gydoc.galleon.domain;

public class ExpenseType extends BizObject {
    
    private String name;
    private String description;
    
    public ExpenseType() {
        
    }
    
    public ExpenseType(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
