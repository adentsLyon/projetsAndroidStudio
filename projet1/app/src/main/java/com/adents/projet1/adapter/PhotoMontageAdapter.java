package com.adents.projet1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adents.projet1.R;
import com.adents.projet1.tools.ImageTools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.PhotoMontage;

/**
 * Created by amachado on 23/06/2015.
 */
public class PhotoMontageAdapter extends ArrayAdapter<PhotoMontage> {

    private List<PhotoMontage> photoList;
    private Context context;

    /**
     * Class constructor
     * @param context
     * @param resource
     * @param objects   photos list to fill the listView
     */

    public PhotoMontageAdapter( Context context, int ressource, List<PhotoMontage> objects) {
        super(context, ressource, objects);
        this.photoList = new ArrayList<>();
        this.photoList.addAll(objects);
        this.context = context;
    }

    public void addPhoto(PhotoMontage p3) {
        photoList.add(p3);
    }

    //Classe de la vue (xml_______)

    private class ViewPhotoHolder{
        TextView tv_name;
        TextView tv_date;
        ImageView iv_photo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewPhotoHolder holder = null;
        if (convertView==null){

            LayoutInflater inflator = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.photo_info,null);

            holder = new ViewPhotoHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);

        }else{
            holder = (ViewPhotoHolder) convertView.getTag();
        }

        //Remplissage de la vue de la photo a la position indiquée

        PhotoMontage p = photoList.get(position);
        holder.tv_name.setText(p.getName());

        Date creationDate = new Date(new File(p.getFilePath()).lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        holder.tv_date.setText(sdf.format(creationDate));

        try {
            holder.iv_photo.setImageBitmap(ImageTools.getBitmapFromAssets(context, p.getFilePath()));
        } catch (IOException e) {
            Log.e("LoadImage","Error on loading image :"+p.getFilePath());
        }
        return convertView;
    }
}
