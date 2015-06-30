package com.example.rartonne.appftur.model;

import java.util.Date;

/**
 * Created by rartonne on 29/06/2015.
 */
public class OrdernrSites {
    private Integer site_id;
    private String ordernr;
    private Integer status_code;
    private Integer modified_by;
    private Date modified_on;
    private Integer installer_id;
    private String name;

    public OrdernrSites(String ordernr, String name) {
        this.ordernr = ordernr;
        this.name = name;
    }

    public Integer getSite_id() {
        return site_id;
    }

    public void setSite_id(Integer site_id) {
        this.site_id = site_id;
    }

    public String getOrdernr() {
        return ordernr;
    }

    public void setOrdernr(String ordernr) {
        this.ordernr = ordernr;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public Integer getModified_by() {
        return modified_by;
    }

    public void setModified_by(Integer modified_by) {
        this.modified_by = modified_by;
    }

    public Date getModified_on() {
        return modified_on;
    }

    public void setModified_on(Date modified_on) {
        this.modified_on = modified_on;
    }

    public Integer getInstaller_id() {
        return installer_id;
    }

    public void setInstaller_id(Integer installer_id) {
        this.installer_id = installer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
