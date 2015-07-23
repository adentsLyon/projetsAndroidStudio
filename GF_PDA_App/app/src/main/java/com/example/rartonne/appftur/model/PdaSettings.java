package com.example.rartonne.appftur.model;

import java.util.Date;

/**
 * Created by rartonne on 22/07/2015.
 */
public class PdaSettings {
    private String pda_id;
    private String pda_name;
    private String land_id;
    private Integer status_code;
    private Date last_update;

    public PdaSettings(String pda_id, String pda_name, String land_id, Integer status_code, Date last_update) {
        this.pda_id = pda_id;
        this.pda_name = pda_name;
        this.land_id = land_id;
        this.status_code = status_code;
        this.last_update = last_update;
    }

    public String getPda_id() {
        return pda_id;
    }

    public void setPda_id(String pda_id) {
        this.pda_id = pda_id;
    }

    public String getPda_name() {
        return pda_name;
    }

    public void setPda_name(String pda_name) {
        this.pda_name = pda_name;
    }

    public String getLand_id() {
        return land_id;
    }

    public void setLand_id(String land_id) {
        this.land_id = land_id;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
}
