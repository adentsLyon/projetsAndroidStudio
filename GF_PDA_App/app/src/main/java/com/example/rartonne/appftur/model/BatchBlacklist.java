package com.example.rartonne.appftur.model;

/**
 * Created by rartonne on 09/07/2015.
 */
public class BatchBlacklist {
    private String batch_nr;
    private String article_id;

    public BatchBlacklist(String batch_nr, String article_id) {
        this.batch_nr = batch_nr;
        this.article_id = article_id;
    }

    public String getBatch_nr() {
        return batch_nr;
    }

    public void setBatch_nr(String batch_nr) {
        this.batch_nr = batch_nr;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }
}
