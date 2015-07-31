package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 30/07/2015.
 */
public class Translation {
    private String id_content;
    private String content;

    public Translation(String id_content, String content) {
        this.id_content = id_content;
        this.content = content;
    }

    public String getId_content() {
        return id_content;
    }

    public void setId_content(String id_content) {
        this.id_content = id_content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
