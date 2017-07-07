package com.xavicortes.xclapp;

import android.content.Context;
import com.xavicortes.xclapp.AppPreferencias;

/**
 * Created by Xavi on 07/07/2017.
 */

public  class Registro {
    AppPreferencias appPref;

    public Registro(AppPreferencias appPref) {
        this.appPref = appPref;
    }

    public boolean ElUsuarioExiste(String username) {
        return username == appPref.getCredenciales() ;
    }

    public void RegistrarUsuario(String usuario) {
        appPref.setCredenciales(usuario);
        appPref.setPrimerVegada(true);
    }
    public String UsuarioActual(){
        return appPref.getCredenciales();
    }

}
