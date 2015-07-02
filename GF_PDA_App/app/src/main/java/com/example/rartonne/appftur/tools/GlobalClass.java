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
    private static String jobNumber = "";
    private static String installerName = "";

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        GlobalClass.login = login;
    }

    public static Integer getInstaller_id() {
        return installer_id;
    }

    public static void setInstaller_id(Integer installer_id) {
        GlobalClass.installer_id = installer_id;
    }

    public static String getLastUpdate() {
        return lastUpdate;
    }

    public static void setLastUpdate(String lastUpdate) {
        GlobalClass.lastUpdate = lastUpdate;
    }

    public static String getWelderCertificate() {
        return welderCertificate;
    }

    public static void setWelderCertificate(String welderCertificate) {
        GlobalClass.welderCertificate = welderCertificate;
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        GlobalClass.userId = userId;
    }

    public static boolean isCheckJob() {
        return checkJob;
    }

    public static void setCheckJob(boolean checkJob) {
        GlobalClass.checkJob = checkJob;
    }

    public static boolean isCheckInstallation() {
        return checkInstallation;
    }

    public static void setCheckInstallation(boolean checkInstallation) {
        GlobalClass.checkInstallation = checkInstallation;
    }

    public static boolean isCheckGeo() {
        return checkGeo;
    }

    public static void setCheckGeo(boolean checkGeo) {
        GlobalClass.checkGeo = checkGeo;
    }

    public static boolean isCheckWelding() {
        return checkWelding;
    }

    public static void setCheckWelding(boolean checkWelding) {
        GlobalClass.checkWelding = checkWelding;
    }

    public static boolean isCheckPictures() {
        return checkPictures;
    }

    public static void setCheckPictures(boolean checkPictures) {
        GlobalClass.checkPictures = checkPictures;
    }

    public static boolean isCheckComment() {
        return checkComment;
    }

    public static void setCheckComment(boolean checkComment) {
        GlobalClass.checkComment = checkComment;
    }

    public static String getGf_sec_id() {
        return gf_sec_id;
    }

    public static void setGf_sec_id(String gf_sec_id) {
        GlobalClass.gf_sec_id = gf_sec_id;
    }

    public static String getArt_id() {
        return art_id;
    }

    public static void setArt_id(String art_id) {
        GlobalClass.art_id = art_id;
    }

    public static String getDesignation() {
        return designation;
    }

    public static void setDesignation(String designation) {
        GlobalClass.designation = designation;
    }

    public static String getDruck() {
        return druck;
    }

    public static void setDruck(String druck) {
        GlobalClass.druck = druck;
    }

    public static String getSdr() {
        return sdr;
    }

    public static void setSdr(String sdr) {
        GlobalClass.sdr = sdr;
    }

    public static String getDim() {
        return dim;
    }

    public static void setDim(String dim) {
        GlobalClass.dim = dim;
    }

    public static String getCatalog() {
        return catalog;
    }

    public static void setCatalog(String catalog) {
        GlobalClass.catalog = catalog;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        GlobalClass.status = status;
    }

    public static boolean isWmModeSingle() {
        return wmModeSingle;
    }

    public static void setWmModeSingle(boolean wmModeSingle) {
        GlobalClass.wmModeSingle = wmModeSingle;
    }

    public static String getJobNumber() {
        return jobNumber;
    }

    public static void setJobNumber(String jobNumber) {
        GlobalClass.jobNumber = jobNumber;
    }

    public static String getInstallerName() {
        return installerName;
    }

    public static void setInstallerName(String installerName) {
        GlobalClass.installerName = installerName;
    }
}
