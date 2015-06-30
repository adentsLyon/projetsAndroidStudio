package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 29/06/2015.
 */
public class Operator {
    private Integer id;
    private String operator_id;
    private Integer installer_id;
    private Integer status_code;
    private String name;
    private String id_long;
    private Integer user_id;

    public Operator(String operator_id) {
        this.operator_id = operator_id;
    }

    public Operator(Integer id, String operator_id, Integer installer_id, Integer status_code, String name, String id_long, Integer user_id) {
        this.id = id;
        this.operator_id = operator_id;
        this.installer_id = installer_id;
        this.status_code = status_code;
        this.name = name;
        this.id_long = id_long;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public Integer getInstaller_id() {
        return installer_id;
    }

    public void setInstaller_id(Integer installer_id) {
        this.installer_id = installer_id;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_long() {
        return id_long;
    }

    public void setId_long(String id_long) {
        this.id_long = id_long;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
