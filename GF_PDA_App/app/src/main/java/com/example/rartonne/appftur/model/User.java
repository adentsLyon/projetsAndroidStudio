package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 29/06/2015.
 */
public class User {
    private Integer user_id;
    private String login;
    private Integer installer_id;
    private String installer_name;
    private Integer customer_id;

    public User(Integer user_id, String login, Integer installer_id, String installer_name, Integer customer_id) {
        this.user_id = user_id;
        this.login = login;
        this.installer_id = installer_id;
        this.installer_name = installer_name;
        this.customer_id = customer_id;
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

    public Integer getInstaller_id() {
        return installer_id;
    }

    public void setInstaller_id(Integer installer_id) {
        this.installer_id = installer_id;
    }

    public String getInstaller_name() {
        return installer_name;
    }

    public void setInstaller_name(String installer_name) {
        this.installer_name = installer_name;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }
}
