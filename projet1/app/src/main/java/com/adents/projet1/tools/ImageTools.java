package com.adents.projet1.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by amachado on 23/06/2015.
 */
public class ImageTools {

    //Méthode qui prend en paramètre le nom du fichier =>Bitmap
    public static Bitmap getBitmapFromAssets(Context context,
                                             String filePath) throws IOException {
        InputStream is = context.getAssets().open(filePath);
        return BitmapFactory.decodeStream(is);
    }

    public static Drawable getDrawableFromAssets(Context context,
                                                 String filePath) throws IOException {
        InputStream is = context.getAssets().open(filePath);
        return Drawable.createFromStream(is,null);
    }

}
