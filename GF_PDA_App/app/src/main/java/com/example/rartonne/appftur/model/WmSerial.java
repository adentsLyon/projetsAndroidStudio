package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 21/07/2015.
 */
public class WmSerial {
    private Integer serial_id;
    private String serial_number;
    private Integer installer_id;
    private String application;
    private String machine;

    public WmSerial(Integer serial_id, String serial_number, Integer installer_id, String application, String machine) {
        this.serial_id = serial_id;
        this.serial_number = serial_number;
        this.installer_id = installer_id;
        this.application = application;
        this.machine = machine;
    }

    public Integer getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(Integer serial_id) {
        this.serial_id = serial_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public Integer getInstaller_id() {
        return installer_id;
    }

    public void setInstaller_id(Integer installer_id) {
        this.installer_id = installer_id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }
}
