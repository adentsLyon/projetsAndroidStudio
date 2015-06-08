package com.example.gponcet.myfirstapp;

/**
 * Created by gponcet on 19/05/2015.
 *
 *
 * Classe de définition d'un article GF_
 *
 *
 */
public class Article {

    private String _article_id;
    private String _designation;

    public Article(){}

    public Article(String article_id, String designation){
        this._article_id = article_id;
        this._designation = designation;
    }

    public String getId() {
        return _article_id;
    }

    public void setId(String article_id) {
        this._article_id = article_id;
    }

    public String getDesignation() {
        return _designation;
    }

    public String toString(){
        return "Article ID : " + getId() + "\nDesignation : " + getDesignation();
    }
}
