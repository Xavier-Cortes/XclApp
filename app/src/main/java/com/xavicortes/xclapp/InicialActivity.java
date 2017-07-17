package com.xavicortes.xclapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xavicortes.xclapp.comu.AppPreferencies;
import com.xavicortes.xclapp.comu.Compartit;
import com.xavicortes.xclapp.comu.DadesUsuari;

/**
 * Created by Xavi on 06/07/2017.
 */


// acivity inicial
// es on es determina si cal indentificar-se, o bé s'incia la activitat principal
// també es on s'inicien les instancies compartides
public class InicialActivity
        extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicialactivity);


        // Anota referencia de les instancies que es faran servir en tota l'aplicació
        Compartit.context = getApplicationContext();
        Compartit.AppPref = AppPreferencies.getInstance();
        Compartit.DadesUsu = DadesUsuari.getInstance();

        ferLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void ferLogin() {
        Intent i ;
        if( Compartit.AppPref.esPrimeraVegada() ) {
            i =new Intent(this, LoginActivity.class);

        }else{
            i =new Intent(this, MainActivity.class);

        }

        startActivity(i);
        finish();
    }


}
