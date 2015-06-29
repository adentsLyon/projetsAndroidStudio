package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 29/06/2015.
 */
public class Fitting {
    private String art_id = "";
    private String designation = "";
    private String druck = "";
    private String sdr = "";
    private String dim = "";
    private String catalog = "";

    public Fitting() {
    }

    public Fitting(String art_id, String designation, String druck, String sdr, String dim, String catalog) {
        this.art_id = art_id;
        this.designation = designation;
        this.druck = druck;
        this.sdr = sdr;
        this.dim = dim;
        this.catalog = catalog;
    }

    public String getArt_id() {
        return art_id;
    }

    public void setArt_id(String art_id) {
        this.art_id = art_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDruck() {
        return druck;
    }

    public void setDruck(String druck) {
        this.druck = druck;
    }

    public String getSdr() {
        return sdr;
    }

    public void setSdr(String sdr) {
        this.sdr = sdr;
    }

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
