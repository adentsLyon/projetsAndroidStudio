package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by rartonne on 29/06/2015.
 */
public abstract class DaoBase<T> {

    protected DataBaseHelper dbHelper;
    protected SQLiteDatabase db;

    public DaoBase(Context context){
        dbHelper = new DataBaseHelper(context);
    }

    public SQLiteDatabase open() throws SQLException {
        return dbHelper.getWritableDatabase();

    }

    public synchronized void close() throws SQLException {
        if(db != null)
            db.close();
    }





}
