package org.expensetracker.model;

import org.expensetracker.db.DataEntity;

public class User extends DataEntity {
    private Integer id;
    private String username;
    private String password;

    public User() {
        super("users");
    }

    public User(Integer id, String username, String password) {
        super("users");
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object clone() {
        return new User(id, username, password);
    }
}
