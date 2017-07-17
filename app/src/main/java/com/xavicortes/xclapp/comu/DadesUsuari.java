package com.xavicortes.xclapp.comu;


import com.xavicortes.xclapp.database.RegistroDBHelper;

import java.util.ArrayList;

// Fa de interface entre la fitxa de usuari la clase de la base de dades
public class DadesUsuari {
    static DadesUsuari StaticInstDadesUsuari;
    public static  RegistroDBHelper DB;


    public  DadesUsuari() {
        DB = RegistroDBHelper.getInstance(Compartit.context);
    }


    public static DadesUsuari getInstance() {
        if( StaticInstDadesUsuari == null ) {
            StaticInstDadesUsuari = new DadesUsuari();
        }
        return StaticInstDadesUsuari;
    }

    public static boolean ElUsuarioExiste(String usuario) {
        FitxaUsuari fu;
        fu = DB.queryRowByNom(usuario.toUpperCase());
        if (fu == null) return false; else return fu.getNom().toUpperCase().equals(usuario.toUpperCase());
    }

    public static boolean ComprovarCredencials(String usuari, String contrasenya) {
        FitxaUsuari fu;
        fu = DB.queryRowByNom(usuari.toUpperCase());
        if (fu == null)
                return false;
        else
            return fu.getNom().toUpperCase().equals(usuari.toUpperCase()) && fu.getContrasenya().equals(contrasenya);
    }

    public static void RegistrarUsuario(String usuario,String Contrasenya) {
        FitxaUsuari fu = new FitxaUsuari();
        fu.setNom(usuario.toUpperCase());
        fu.setContrasenya(Contrasenya);
        Long ID = DB.createRow(fu);
        Compartit.AppPref.setUserID(ID);
        Compartit.AppPref.setPrimerVegada(false);

    }

    public static Long IDUsuarioActual(){
        return Compartit.AppPref.getUserID();
    }

    public static FitxaUsuari getFitxaUsuari() {
         return DB.queryRowByID(IDUsuarioActual());
    }

    public static void setFitxaUsuari(FitxaUsuari fu) { DB.updateRow(fu); }

    public static ArrayList<FitxaUsuari> ClassificacioUsuaris() {
        return DB.queryRowsToListAdapter();
    }
    public void ActivarUsuari(String usuario) {
        FitxaUsuari fu;
        fu = DB.queryRowByNom(usuario.toUpperCase());
        Compartit.AppPref.setUserID(fu.getID());
        Compartit.AppPref.setPrimerVegada(false);
    }
}


