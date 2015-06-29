package com.adents.projet1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by amachado on 24/06/2015.
 */
public class DaoBase<U> {

    protected DataBaseHelper dbHelper;
    protected SQLiteDatabase db;

    public DaoBase(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public SQLiteDatabase open() throws SQLException{
        return dbHelper.getWritableDatabase();
    }

    public synchronized void close() throws SQLException{

        if (db != null)
            db.close();
    }
}
