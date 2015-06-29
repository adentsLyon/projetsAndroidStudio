package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 29/06/2015.
 */
public class User {
    private Integer user_id;
    private String login;

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
