package com.xavicortes.xclapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;


/**
 * Created by Xavi on 06/07/2017.
 */

public class NoLogActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btReintentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nologonactivity);


        btReintentar = (Button) findViewById(R.id.NoLogon_Reintentar);
        btReintentar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id  = v.getId();
        switch ( id ) {
            case R.id.NoLogon_Reintentar:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

        }

    }
}
