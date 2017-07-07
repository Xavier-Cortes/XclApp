package com.xavicortes.xclapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.*;

/**
 * Created by Xavi on 06/07/2017.
 */

public  class AppPreferencias implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences sp;
    static String sAPP = "APP";
    static String sPrimeraVegada = "PrimeraVegada";
    static String sUsername = "UserName";
    Context context;

    public AppPreferencias(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(sAPP,Context.MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(this);
    }


    public boolean esPrimeraVegada() {
         boolean b = sp.getBoolean(sPrimeraVegada, true);
         return b;
    }

    public void setPrimerVegada(boolean b) {
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(sPrimeraVegada, b);
        editor.apply();
    }


    public void setCredenciales(String username){
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(sUsername, username);
        editor.apply();

    }
    public String getCredenciales() {
        return sp.getString(sUsername,"");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key == sPrimeraVegada) {
            Toast.makeText(context, String.format("onSharedPreferenceChanged%s", key),Toast.LENGTH_SHORT).show();
        }
    }
}
