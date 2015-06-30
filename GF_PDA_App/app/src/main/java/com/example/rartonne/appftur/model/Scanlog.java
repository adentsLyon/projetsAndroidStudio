package com.example.rartonne.appftur.model;

import java.util.Date;

/**
 * Created by rartonne on 29/06/2015.
 */
public class Scanlog {
    private String gf_sec_id;
    private double gps_lat;
    private double gps_long;
    private Date scan_date;
    private Integer user_id;
    private Integer status_code;
    private String art_id;
    private String customer_order_nr;
    private String welding_sketch_nr;
    private String serial_wm_nr;
    private Integer fusion_nr;
    private String source;
    private String status_name;
    private String login;

    //constructeur pour affichage
    public Scanlog(String gf_sec_id, double gps_lat, double gps_long, Date scan_date, String login, String status_name, String art_id, String customer_order_nr, String welding_sketch_nr, String serial_wm_nr, Integer fusion_nr, String source) {
        this.gf_sec_id = gf_sec_id;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
        this.scan_date = scan_date;
        this.art_id = art_id;
        this.customer_order_nr = customer_order_nr;
        this.welding_sketch_nr = welding_sketch_nr;
        this.serial_wm_nr = serial_wm_nr;
        this.fusion_nr = fusion_nr;
        this.source = source;
        this.status_name = status_name;
        this.login = login;
    }

    public Scanlog(String gf_sec_id, Integer user_id, String art_id) {
        this.gf_sec_id = gf_sec_id;
        this.user_id = user_id;
        this.art_id = art_id;
    }

    public String getGf_sec_id() {
        return gf_sec_id;
    }

    public void setGf_sec_id(String gf_sec_id) {
        this.gf_sec_id = gf_sec_id;
    }

    public double getGps_lat() {
        return gps_lat;
    }

    public void setGps_lat(double gps_lat) {
        this.gps_lat = gps_lat;
    }

    public double getGps_long() {
        return gps_long;
    }

    public void setGps_long(double gps_long) {
        this.gps_long = gps_long;
    }

    public Date getScan_date() {
        return scan_date;
    }

    public void setScan_date(Date scan_date) {
        this.scan_date = scan_date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getArt_id() {
        return art_id;
    }

    public void setArt_id(String art_id) {
        this.art_id = art_id;
    }

    public String getCustomer_order_nr() {
        return customer_order_nr;
    }

    public void setCustomer_order_nr(String customer_order_nr) {
        this.customer_order_nr = customer_order_nr;
    }

    public String getWelding_sketch_nr() {
        return welding_sketch_nr;
    }

    public void setWelding_sketch_nr(String welding_sketch_nr) {
        this.welding_sketch_nr = welding_sketch_nr;
    }

    public String getSerial_wm_nr() {
        return serial_wm_nr;
    }

    public void setSerial_wm_nr(String serial_wm_nr) {
        this.serial_wm_nr = serial_wm_nr;
    }

    public Integer getFusion_nr() {
        return fusion_nr;
    }

    public void setFusion_nr(Integer fusion_nr) {
        this.fusion_nr = fusion_nr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "SEC : " + this.gf_sec_id +
                "\nLAT : " + this.gps_lat +
                "\nLONG : " + this.gps_long +
                "\nDATE : " + this.scan_date +
                "\nLOGIN : " + this.login +
                "\nSTATUS : " + this.status_name +
                "\nART : " + this.art_id +
                "\nORDER : " +this.customer_order_nr +
                "\nSKETCH : " + this.welding_sketch_nr +
                "\nWM : " + this.serial_wm_nr +
                "\nFUSION : " + this.fusion_nr +
                "\nSOURCE : " + this.source +
                " \n\n\n";
    }
}
