package com.example.rartonne.appftur.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rartonne.appftur.R;

/**
 * Created by rartonne on 11/06/2015.
 */
public class GlobalClass {

    private static String login = "";
    private static Integer installer_id;
    private static String lastUpdate = "";
    private static String welderCertificate = "";
    private static Integer userId;
    private static boolean checkJob = false;
    private static boolean checkInstallation = false;
    private static boolean checkGeo = false;
    private static boolean checkWelding = false;
    private static boolean checkPictures = false;
    private static boolean checkComment = false;
    private static String gf_sec_id = "";
    private static String art_id = "";
    private static String designation = "";
    private static String druck = "";
    private static String sdr = "";
    private static String dim = "";
    private static String catalog = "";
    private static String status = "sign_login_expected";
    private static boolean wmModeSingle = true;

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String newLogin) {
        login = newLogin;
    }

    public static boolean getCheckJob(){
        return checkJob;
    }

    public static void setCheckJob(){
        if(checkJob == true){
            checkJob = false;
        }else{
            checkJob = true;
        }
    }

    public static boolean getCheckInstallation(){
        return checkInstallation;
    }
    public static boolean getCheckGeo(){
        return checkGeo;
    }

    public static boolean getCheckWelding(){
        return checkWelding;
    }

    public static boolean getCheckPictures(){
        return checkPictures;
    }

    public static boolean getCheckComment(){
        return checkComment;
    }

    public static String getWelderCertificate(){
        return welderCertificate;
    }

    public static void setWelderCertificate(String certif){
        welderCertificate = certif;
    }

    public static Integer getUserId(){
        return userId;
    }

    public static void setUserId(Integer newId){
        userId = newId;
    }

    public static String getArt_id(){ return art_id; }

    public static String getDesignation(){ return designation; }

    public static String getDruck(){ return druck; }

    public static String getSdr(){ return sdr; }

    public static String getDim(){ return dim; }

    public static String getCatalog(){ return catalog; }

    public static String getStatus(){ return status; }

    public static void setArt_id(String newArt_id){
        art_id = newArt_id;
    }

    public static void setDesignation(String newDesignation){
        designation = newDesignation;
    }

    public static void setDruck(String newDruck){
        druck = newDruck;
    }

    public static void setSdr(String newSdr){
        sdr = newSdr;
    }

    public static void setDim(String newDim){
        dim = newDim;
    }

    public static void setCatalog(String newCatalog){
        catalog = newCatalog;
    }

    public static void setStatus(String newStatus){
        status = newStatus;
    }

    public static String getGf_sec_id() {
        return gf_sec_id;
    }

    public static void setGf_sec_id(String gf_sec_id) {
        GlobalClass.gf_sec_id = gf_sec_id;
    }

    public static Integer getInstaller_id() {
        return installer_id;
    }

    public static void setInstaller_id(Integer installer_id) {
        GlobalClass.installer_id = installer_id;
    }

    public static boolean isWmModeSingle() {
        return wmModeSingle;
    }

    public static void setWmModeSingle(boolean wmModeSingle) {
        GlobalClass.wmModeSingle = wmModeSingle;
    }
}
