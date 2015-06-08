package com.example.gponcet.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by gponcet on 19/05/2015.
 */
public class SQLiteService extends SQLiteOpenHelper{

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "gf_mobiledb";

    private static final String TABLE_ARTICLE = "article";
    private static final String COL_ID = "article_id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_DESIGNATION = "designation";
    private static final int NUM_COL_DESIGNATION = 1;
    private SQLiteDatabase _db;

    private final Context _mycontext;
    private static final int DATABASE_VERSION = 1; // l'incrément appelle onUpgrade(), décrément => onDowngrade()
    private String DATABASE_PATH; // chemin défini par le constructeur
    private static final String DATABASE_NAME = "gf_pda_db.sqlite";

    public SQLiteService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this._mycontext=context;
        String filesDir = context.getFilesDir().getPath(); // /data/data/com.package.nom/files/
        DATABASE_PATH = filesDir.substring(0, filesDir.lastIndexOf("/")) + "/databases/"; // /data/data/com.package.nom/databases/

        String messageToast = "copy db de assets vers le répertoire";
        // Si la bdd n'existe pas dans le dossier de l'app
        if (!checkdatabase()) {
            // copy db de 'assets' vers DATABASE_PATH
            copydatabase();
        }
        else
        {
            messageToast = "Pas de copie, base trouvée.";
        }

        /*Toast myToast = Toast.makeText(context, messageToast, Toast.LENGTH_SHORT);
        myToast.show();*/

    }

    /**
     * Ouvre la BDD en écriture
     */
    public void open() {
        _db = this.getWritableDatabase();
    }

    /**
     * Ferme l'accès à la BDD
     */
    public void close() {
        _db.close();
    }


    private boolean checkdatabase() {
        // retourne true/false si la bdd existe dans le dossier de l'app
        File dbfile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbfile.exists();
    }

    // On copie la base de "assets" vers "/data/data/com.package.nom/databases"
    // ceci est fait au premier lancement de l'application
    private void copydatabase() {

        final String outFileName = DATABASE_PATH + DATABASE_NAME;

        InputStream myInput;
        try {
            // Ouvre la bdd de 'assets' en lecture
            myInput = _mycontext.getAssets().open(DATABASE_NAME);

            // dossier de destination
            File pathFile = new File(DATABASE_PATH);
            if(!pathFile.exists()) {
                if(!pathFile.mkdirs()) {
                    Toast.makeText(_mycontext, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Ouverture en écriture du fichier bdd de destination
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfert de inputfile vers outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Fermeture
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(_mycontext, "Erreur : copydatabase()", Toast.LENGTH_SHORT).show();
        }

        // on greffe le num?ro de version
        try{
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion(DATABASE_VERSION);
        }
        catch(SQLiteException e) {
            // bdd n'existe pas
        }

    } // copydatabase()



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Article getArticleById(String article_id){
        //Récupère dans un Cursor les valeurs correspondant à un article contenu dans la BDD (ici on sélectionne l'article grâce à son id)
        Cursor c = _db.query(TABLE_ARTICLE, new String[] {COL_ID, COL_DESIGNATION}, COL_ID + " LIKE \"" + article_id +"\"", null, null, null, null);
        return cursorToArticle(c);
    }

    public long insertArticle(Article anArticle)
    {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID, anArticle.getId());
        values.put(COL_DESIGNATION, anArticle.getDesignation());
        //on insère l'objet dans la BDD via le ContentValues
        return _db.insert(TABLE_ARTICLE, null, values);
    }

    //Cette méthode permet de convertir un cursor en un article
    private Article cursorToArticle(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        // Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une instance de la classe Article
        Article article = new Article(c.getString(NUM_COL_ID), c.getString(NUM_COL_DESIGNATION));
        // On ferme le cursor
        c.close();

        // On retourne l'objet Article
        return article;
    }





}
