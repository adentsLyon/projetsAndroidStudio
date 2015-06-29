package com.adents.projet1;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amachado on 23/06/2015.
 */
public class DbHelper extends SQLiteOpenHelper {



    private final static String DB_NAME = "bddFormation";
    private static int numVersion = 1;


    public DbHelper(Context context){
        super(context,DB_NAME,null,numVersion);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table articles(id integer primary key AUTOINCREMENT, description varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            //TODO nouvelles creations
        }
    }
}
