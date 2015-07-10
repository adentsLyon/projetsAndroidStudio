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
    private static Integer customer_id;
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
    private static String batch_nr = "";
    private static boolean isBlacklisted;
    private static String status = "sign_login_expected";
    private static boolean wmModeSingle = true;
    private static String jobNumber = "";
    private static String installerName = "";
    private static String e1 = "";
    private static String e2 = "";
    private static String e3 = "";
    private static String e4 = "";
    private static String l1 = "";
    private static String l2 = "";
    private static String l3 = "";
    private static String l4 = "";

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

    public static String getE1() {
        return e1;
    }

    public static void setE1(String e1) {
        GlobalClass.e1 = e1;
    }

    public static String getE2() {
        return e2;
    }

    public static void setE2(String e2) {
        GlobalClass.e2 = e2;
    }

    public static String getE3() {
        return e3;
    }

    public static void setE3(String e3) {
        GlobalClass.e3 = e3;
    }

    public static String getE4() {
        return e4;
    }

    public static void setE4(String e4) {
        GlobalClass.e4 = e4;
    }

    public static String getL1() {
        return l1;
    }

    public static void setL1(String l1) {
        GlobalClass.l1 = l1;
    }

    public static String getL2() {
        return l2;
    }

    public static void setL2(String l2) {
        GlobalClass.l2 = l2;
    }

    public static String getL3() {
        return l3;
    }

    public static void setL3(String l3) {
        GlobalClass.l3 = l3;
    }

    public static String getL4() {
        return l4;
    }

    public static void setL4(String l4) {
        GlobalClass.l4 = l4;
    }

    public static Integer getCustomer_id() {
        return customer_id;
    }

    public static void setCustomer_id(Integer customer_id) {
        GlobalClass.customer_id = customer_id;
    }

    public static String getBatch_nr() {
        return batch_nr;
    }

    public static void setBatch_nr(String batch_nr) {
        GlobalClass.batch_nr = batch_nr;
    }

    public static boolean isBlacklisted() {
        return isBlacklisted;
    }

    public static void setIsBlacklisted(boolean isBlacklisted) {
        GlobalClass.isBlacklisted = isBlacklisted;
    }
}
