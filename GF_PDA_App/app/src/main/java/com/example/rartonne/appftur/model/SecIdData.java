package com.example.rartonne.appftur.model;

import java.util.Date;

/**
 * Created by rartonne on 02/07/2015.
 */
public class SecIdData {
    private Integer data_id;
    private String type;
    private String value;
    private Date createdon;
    private Date modifiedon;

    public SecIdData(Integer data_id, String type, String value, Date createdon, Date modifiedon) {
        this.data_id = data_id;
        this.type = type;
        this.value = value;
        this.createdon = createdon;
        this.modifiedon = modifiedon;
    }

    public Integer getData_id() {
        return data_id;
    }

    public void setData_id(Integer data_id) {
        this.data_id = data_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public Date getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(Date modifiedon) {
        this.modifiedon = modifiedon;
    }

    @Override
    public String toString() {
        return this.createdon.toString();
    }
}
