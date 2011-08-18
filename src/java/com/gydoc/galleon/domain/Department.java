package com.gydoc.galleon.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Department extends BizObject {

    private String name;
    private String code;
    private String description;
    private List<User> users = new ArrayList<User>();
    private User manager;

    public Department() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public void addUser(User user) {
        users.add(user);
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
    
    public Long getManagerId() {
        return getManager() != null ? getManager().getId() : null;
    }
    
}
