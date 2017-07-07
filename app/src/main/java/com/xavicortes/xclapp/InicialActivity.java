package com.xavicortes.xclapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Xavi on 06/07/2017.
 */



public class InicialActivity
        extends AppCompatActivity
{

    AppPreferencias appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicialactivity);

        appPref = new AppPreferencias(getApplicationContext());

        doLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void doLogin() {
        //appPref.setPrimerVegada(true);
        Intent i ;
        if( appPref.esPrimeraVegada() ) {

            i =new Intent(this, LoginActivity.class);

        }else{
            i =new Intent(this, MainActivity.class);

        }

        startActivity(i);
        finish();
    }


}
