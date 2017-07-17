package com.xavicortes.xclapp.database;

import android.provider.BaseColumns;

public final class RegistroDBContract {
    //We prevent that someone extends this class by making it final

    /*
    * DEFINITION:
    * A formal declaration of how the database is organized.
    * Definition by Google in: https://developer.android.com/training/basics/data-storage/databases.html
    */

    // We prevent that someone instantiate this class making the constructor private
    private RegistroDBContract(){}

    //We create as much public static classes as tables we have in our database
    public static class Registro implements BaseColumns {
        public static final String TABLE_NAME = "Registro1";
        //public static final String COLUMN_ID = "id"; <- Actually we don't need a dedicated COLUMN_ID
        //because this class implements BaseColumns which has a _ID constant for that
        public static final String COLUMN_NOMBREUSUARIO = "Nom";
        public static final String COLUMN_CONTRASENYA = "Contrasenya";
        public static final String COLUMN_ADREÇA = "Adreça";
        public static final String COLUMN_NOTIFICACIONS = "Notificacions";
        public static final String COLUMN_INTENTS = "Intents";
        public static final String COLUMN_IMATGE = "Imatge";
    }

    //Declare here other inner classes for other tables in the database using the same format

    /*
    public static class Table2 implements BaseColumns{
        public static final String TABLE_NAME = "othertable";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_PHONE = "phone";
    }
    */


}
