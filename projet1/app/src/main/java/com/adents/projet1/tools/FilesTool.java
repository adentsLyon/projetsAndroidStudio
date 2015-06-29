package com.adents.projet1.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by amachado on 25/06/2015.
 * Classes avec des méthodes de manipulation de fichiers
 * voir : http://developer.android.com/training/basics/data-storage/files.html
 */
public class FilesTool {

    /**
     * Write a file with content in a file on the specified folder
     * @param mode      specify if the file is public or private Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE
     * @param folder    folder where to store the file, created if not exists
     * @param fileName  name of the file
     * @return
     */
    public static boolean writeFileInInternalStorage(Activity activity, int mode,
                                                     String folder,
                                                     String fileName,
                                                     byte[] content){
        try {
            BufferedOutputStream outputStream = null;
            String filePath = "";
            if(folder!=null) {
                File folderToCreate = new File(activity.getFilesDir() + "/" + folder);
                if (!folderToCreate.exists())
                    folderToCreate.mkdir();

                filePath = activity.getFilesDir()+"/"+folder+"/"+fileName;
                outputStream = new BufferedOutputStream(new FileOutputStream(filePath));

            }else {
                filePath = fileName;
                outputStream = new BufferedOutputStream(activity.openFileOutput(filePath, mode));
            }

            outputStream.write(content);
            outputStream.close();
            return true;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return false;
        }
    }

    /**
     * Read a file from the internal storage
     * @param activity
     * @param folder
     * @param fileName
     * @return
     */
    public static String readFileFromInternalStorage(Activity activity, String folder, String fileName){
        try {
            String filePath = activity.getFilesDir()+"/";
            if(folder==null)
                filePath+= fileName;
            else
                filePath += folder+"/"+fileName;

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[2048];
            int length = 0;
            while ((length = bis.read(buffer))>0){
                sb.append(new String(buffer, 0, length));
            }
            bis.close();
            return sb.toString();
        }catch(IOException ex){
            return null;
        }
    }

    /**
     * Write temp file
     * @param activity
     * @param fileName
     * @param content
     * @return
     */
    public static boolean writeTempFileInInternalStorage(Activity activity,
                                                         String fileName,
                                                         byte[] content){
        try {
            File myTmpFile = File.createTempFile(fileName,null,activity.getApplicationContext().getCacheDir());
            BufferedOutputStream outputStream =
                    new BufferedOutputStream(new FileOutputStream(myTmpFile));

            outputStream.write(content);
            outputStream.close();
            return true;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return false;
        }
    }


    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean writeFileInExternalStorage(Activity activity,
                                                     String directory,
                                                     String fileName,
                                                     byte[] content){
        if(isExternalStorageWritable()) {
            try {
                BufferedOutputStream outputStream = null;
                File filePath = new File(Environment.getExternalStoragePublicDirectory(directory), fileName);
                outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                outputStream.write(content);
                outputStream.close();
                return true;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                return false;
            }
        }else
            return false;
    }



    public static String readFileFromExternalStorage(Activity activity, String directory, String fileName){
        try{
            File filePath = new File(Environment.getExternalStoragePublicDirectory(directory), fileName);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[2048];
            int length = 0;
            while ((length = bis.read(buffer))>0){
                sb.append(new String(buffer, 0, length));
            }
            bis.close();
            return sb.toString();
        }catch(IOException ex){
            return null;
        }
    }




}