package com.xavicortes.xclapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xavicortes.xclapp.comu.FitxaUsuari;

import java.util.ArrayList;

public class RegistroDBHelper extends SQLiteOpenHelper{

    /*
    * DEFINITION:
    * This class is the one which talks with the DB.
    */
    private final String TAG = "RegistroDBHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "xclApp.db";

    private static final String SQL_CREATE_REGISTRO =
            "CREATE TABLE " + RegistroDBContract.Registro.TABLE_NAME + " (" +
                    RegistroDBContract.Registro._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO + " TEXT," +
                     RegistroDBContract.Registro.COLUMN_CONTRASENYA + " TEXT," +
                     RegistroDBContract.Registro.COLUMN_ADREÇA + " TEXT," +
                     RegistroDBContract.Registro.COLUMN_NOTIFICACIONS + " TEXT," +
                     RegistroDBContract.Registro.COLUMN_INTENTS + " TEXT," +
                     RegistroDBContract.Registro.COLUMN_IMATGE + " TEXT" +
                    ")";

    private static final String SQL_DELETE_REGISTRO =
            "DROP TABLE IF EXISTS " + RegistroDBContract.Registro.TABLE_NAME;

    private static RegistroDBHelper instance;
    private static SQLiteDatabase writable;
    private static SQLiteDatabase readable;

    //We will use this method instead the default constructor to get a reference.
    //With this we will use all the time the same reference.
    public static RegistroDBHelper getInstance(Context c){
        if(instance == null){
            instance = new RegistroDBHelper(c);
            if (writable == null) writable = instance.getWritableDatabase();
            if (readable == null) readable = instance.getReadableDatabase();
        }
        return instance;
    }

    //With this, all must use getInstance(Context) to use this class
    private RegistroDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //We execute here the SQL sentence to create the DB
        sqLiteDatabase.execSQL(SQL_CREATE_REGISTRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //This method will be executed when the system detects that DATABASE_VERSION has been upgraded
        sqLiteDatabase.execSQL(SQL_DELETE_REGISTRO);
        onCreate(sqLiteDatabase);
    }
    public void EliminarTabla() {
        getWritableDatabase().execSQL(SQL_DELETE_REGISTRO);
    }
    public void CrearTabla() {
        getWritableDatabase().execSQL(SQL_CREATE_REGISTRO);
    }

    public long createRow(FitxaUsuari fu) {
        ContentValues values = new ContentValues();
        values.put(RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO, fu.getNom());
        values.put(RegistroDBContract.Registro.COLUMN_CONTRASENYA, fu.getContrasenya());
        values.put(RegistroDBContract.Registro.COLUMN_ADREÇA, fu.getAdreça());
        values.put(RegistroDBContract.Registro.COLUMN_NOTIFICACIONS, fu.getNotificacions());
        values.put(RegistroDBContract.Registro.COLUMN_INTENTS, fu.getIntents());
        values.put(RegistroDBContract.Registro.COLUMN_IMATGE, fu.getImatge());
        long newId = writable.insert(RegistroDBContract.Registro.TABLE_NAME,null,values);
        return newId;
    }

    public int updateRow(FitxaUsuari fu) {
        ContentValues values = new ContentValues();
       // values.put(RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO, s.charAt(0)+s);
        values.put(RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO, fu.getNom());
        values.put(RegistroDBContract.Registro.COLUMN_CONTRASENYA, fu.getContrasenya());
        values.put(RegistroDBContract.Registro.COLUMN_ADREÇA, fu.getAdreça());
        values.put(RegistroDBContract.Registro.COLUMN_NOTIFICACIONS, fu.getNotificacions());
        values.put(RegistroDBContract.Registro.COLUMN_INTENTS, fu.getIntents());
        values.put(RegistroDBContract.Registro.COLUMN_IMATGE, fu.getImatge());
        int rows_afected = readable.update(RegistroDBContract.Registro.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                RegistroDBContract.Registro._ID + " = ? ",                 //Selection args
                new String[] {String.valueOf(fu.getID())});                                                  //Selection values

        return rows_afected;
    }

    public int deleteRow(FitxaUsuari fu) {
        int afected = readable.delete(RegistroDBContract.Registro.TABLE_NAME,         //Table name
                RegistroDBContract.Registro._ID + " = ? ",                 //Selection args
                new String[] {String.valueOf(fu.getID())});                                                  //Selection values

        return afected;
    }

    public FitxaUsuari queryRowByID(Long ID) {
        Cursor c;
        c = readable.query(RegistroDBContract.Registro.TABLE_NAME,    //Table name
                new String[] {
                        RegistroDBContract.Registro._ID
                        , RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO
                        , RegistroDBContract.Registro.COLUMN_CONTRASENYA
                        , RegistroDBContract.Registro.COLUMN_ADREÇA
                        , RegistroDBContract.Registro.COLUMN_NOTIFICACIONS
                        , RegistroDBContract.Registro.COLUMN_INTENTS
                        , RegistroDBContract.Registro.COLUMN_IMATGE

                },       //Columns we select
                RegistroDBContract.Registro._ID + " = ? ",    //Columns for the WHERE clause
                new String[] {String.valueOf(ID)},                                   //Values for the WHERE clause
                null,                                               //Group By
                null,                                               //Having
                null);                                              //Sort

        FitxaUsuari fu = null;

        if (c.moveToFirst()) {

            fu  = CarregarFitxaUsuari(c);

        }

        //Always close the cursor after you finished using it
        c.close();

        return fu;
    }

    public FitxaUsuari queryRowByNom(String Nom) {
        Cursor c;
        c = readable.query(RegistroDBContract.Registro.TABLE_NAME,    //Table name
                new String[] {
                        RegistroDBContract.Registro._ID
                        , RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO
                        , RegistroDBContract.Registro.COLUMN_CONTRASENYA
                        , RegistroDBContract.Registro.COLUMN_ADREÇA
                        , RegistroDBContract.Registro.COLUMN_NOTIFICACIONS
                        , RegistroDBContract.Registro.COLUMN_INTENTS
                        , RegistroDBContract.Registro.COLUMN_IMATGE

                },       //Columns we select
                RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO + " = ? ",    //Columns for the WHERE clause
                new String[] {Nom},                                   //Values for the WHERE clause
                null,                                               //Group By
                null,                                               //Having
                null);                                              //Sort

        FitxaUsuari fu = null;

        if (c.moveToFirst()) {

            fu  = CarregarFitxaUsuari(c);
        }

        //Always close the cursor after you finished using it
        c.close();

        return fu;
    }


    public ArrayList<FitxaUsuari> queryRowsToListAdapter() {
        Cursor c;
        c = readable.query(RegistroDBContract.Registro.TABLE_NAME,    //Table name
                new String[] {
                        RegistroDBContract.Registro._ID
                        , RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO
                        , RegistroDBContract.Registro.COLUMN_CONTRASENYA
                        , RegistroDBContract.Registro.COLUMN_ADREÇA
                        , RegistroDBContract.Registro.COLUMN_NOTIFICACIONS
                        , RegistroDBContract.Registro.COLUMN_INTENTS
                        , RegistroDBContract.Registro.COLUMN_IMATGE

                },       //Columns we select
                RegistroDBContract.Registro.COLUMN_INTENTS +" <>0 ",    //Columns for the WHERE clause
                null,                                   //Values for the WHERE clause
                null,                                               //Group By
                null,                                                          //Having
                RegistroDBContract.Registro.COLUMN_INTENTS + " ASC"     //Sort
        )  ;

        ArrayList<FitxaUsuari> alfu = null;

        if (c.moveToFirst())
            do {

            if (alfu == null)  alfu = new ArrayList<FitxaUsuari>();
            alfu.add(CarregarFitxaUsuari(c));

            } while (c.moveToNext());

        //Always close the cursor after you finished using it
        c.close();

        return alfu;
    }

    public FitxaUsuari CarregarFitxaUsuari(Cursor c) {

        //We go here if the cursor is not empty
        FitxaUsuari r = new FitxaUsuari();

        r.setID(c.getLong(c.getColumnIndex(RegistroDBContract.Registro._ID)));
        r.setNom(c.getString(c.getColumnIndex(RegistroDBContract.Registro.COLUMN_NOMBREUSUARIO)));
        r.setContrasenya(c.getString(c.getColumnIndex(RegistroDBContract.Registro.COLUMN_CONTRASENYA)));
        r.setAdreça(c.getString(c.getColumnIndex(RegistroDBContract.Registro.COLUMN_ADREÇA)));
        r.setNotificacions(c.getString(c.getColumnIndex(RegistroDBContract.Registro.COLUMN_NOTIFICACIONS)));
        r.setIntents(c.getInt(c.getColumnIndex(RegistroDBContract.Registro.COLUMN_INTENTS)));
        r.setImatge(c.getString(c.getColumnIndex(RegistroDBContract.Registro.COLUMN_IMATGE)));
        return r;

    }


    @Override
    public synchronized void close() {
        super.close();
        //Always close the SQLiteDatabase
        writable.close();
        readable.close();
        Log.v(TAG,"close()");
    }
}
