package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.model.Translation;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rartonne on 30/07/2015.
 */
public class TranslationDao extends DaoBase<Translation>{
    public TranslationDao(Context context) {
        super(context);
    }

    public ArrayList<Translation> select(String id_content){
        try {
            db = this.open();
            ArrayList<Translation> translations = new ArrayList<>();

            Cursor cursor =  db.rawQuery("SELECT content FROM translation WHERE id_content = ?",new String[]{id_content});

            while(cursor.moveToNext()){
                Translation translation = new Translation(id_content, cursor.getString(0));
                translations.add(translation);
            }
            cursor.close();

            this.close();
            return translations;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return null;
        }
    }
}
