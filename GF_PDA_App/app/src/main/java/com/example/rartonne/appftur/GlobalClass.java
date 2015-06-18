package com.example.rartonne.appftur;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

/**
 * Created by rartonne on 11/06/2015.
 */
public class GlobalClass extends Application {

    private String login = "Username";
    private String lastUpdate = "";
    private String welderCertificate = "";
    private int userId;
    private boolean checkJob = false;
    private boolean checkInstallation = false;
    private boolean checkGeo = false;
    private boolean checkWelding = false;
    private boolean checkPictures = false;
    private boolean checkComment = false;

    public String getLogin() {
        return login;
    }

    public void setLogin(String newLogin) {
        login = newLogin;
    }

    public boolean getCheckJob(){
        return checkJob;
    }

    public void setCheckJob(){
        if(checkJob == true){
            checkJob = false;
        }else{
            checkJob = true;
        }
    }

    public boolean getCheckInstallation(){
        return checkInstallation;
    }
    public boolean getCheckGeo(){
        return checkGeo;
    }

    public boolean getCheckWelding(){
        return checkWelding;
    }

    public boolean getCheckPictures(){
        return checkPictures;
    }

    public boolean getCheckComment(){
        return checkComment;
    }

    public String getWelderCertificate(){
        return welderCertificate;
    }

    public void setWelderCertificate(String certif){
        welderCertificate = certif;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int newId){
        userId = newId;
    }
}
