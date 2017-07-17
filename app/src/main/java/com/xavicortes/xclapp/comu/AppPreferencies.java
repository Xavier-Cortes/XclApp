package com.xavicortes.xclapp.comu;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.*;


import com.xavicortes.xclapp.Util.MediaPlayerBoundService;

// gestiona les Shared preferences de la aplicació
public  class AppPreferencies implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static AppPreferencies staticInstAppPreferencies;
    private static SharedPreferences staticInstSharedPrefences;
    // Cadenes identificació de les preferences
    private static String sAPP = "APP";
    private static String sPrimeraVegada = "PrimeraVegada";
    private static String sUserID = "UserID";
    private static String sExpresio = "Expressio";
    private static String sAns="ans";
    private static String sResultat = "Resultat";
    private static String sMediaPlayerEstat= "MPEstat";
    private static MediaPlayerBoundService.EstatMediaPlayerEnum estadoMediaplayer;


     public static AppPreferencies getInstance() {
         // si encara no s'ha creat crea una referencia a la clase per tal de que nomes hi hagi una instancia en tota la aplicació
        if (staticInstAppPreferencies == null) {
            staticInstAppPreferencies = new AppPreferencies();
            staticInstSharedPrefences = Compartit.context.getSharedPreferences(sAPP, Context.MODE_PRIVATE);
            staticInstSharedPrefences.registerOnSharedPreferenceChangeListener(staticInstAppPreferencies);
        }
        return staticInstAppPreferencies;
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //Toast.makeText(Compartit.context, String.format("onSharedPreferenceChanged %s", key),Toast.LENGTH_SHORT).show();
    }


    public static boolean esPrimeraVegada() {
         boolean b = staticInstSharedPrefences.getBoolean(sPrimeraVegada, true);
         //Toast.makeText(Compartit.context, "Es primer vegada" + String.valueOf(b), Toast.LENGTH_SHORT).show();
         return b;
    }

    public static void setPrimerVegada(boolean b) {
        SharedPreferences.Editor editor= staticInstSharedPrefences.edit();
        editor.putBoolean(sPrimeraVegada, b);
        editor.apply();
    }


    public static void setUserID(Long ID){
        SharedPreferences.Editor editor= staticInstSharedPrefences.edit();
        editor.putString(sUserID,String.valueOf( ID));
        editor.apply();

    }
    public static Long getUserID() { return Long.valueOf(staticInstSharedPrefences.getString(sUserID,"0"));}

    public static void setExpressio(String dada ) {
        SharedPreferences.Editor editor= staticInstSharedPrefences.edit();
        editor.putString(sExpresio, dada);
        editor.apply();
    }

    public static String getExpresio() { return staticInstSharedPrefences.getString(sExpresio,"");}

    public static void setAns(String dada ) {
        SharedPreferences.Editor editor= staticInstSharedPrefences.edit();
        editor.putString(sAns, dada);
        editor.apply();
    }
    public static Double getAns() { return Double.valueOf(staticInstSharedPrefences.getString(sAns,"0.0"));}

    public static void setResultat(String dada ) {
        SharedPreferences.Editor editor= staticInstSharedPrefences.edit();
        editor.putString(sResultat, dada);
        editor.apply();
    }
    public static  String getResultat() { return staticInstSharedPrefences.getString(sResultat,"0.0");}

    public static void setEstadoMediaplayer(String dada) {
        SharedPreferences.Editor editor= staticInstSharedPrefences.edit();
        editor.putString(sMediaPlayerEstat, dada);
        editor.apply();
    }

    public static String getEstadoMediaplayer() {
        return staticInstSharedPrefences.getString(sMediaPlayerEstat,"");
    }
}
