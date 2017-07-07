package com.xavicortes.xclapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

/**
 * Created by Xavi on 06/07/2017.
 */

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    AppPreferencias appPref;
    Registro uReg;

    Button btnRegister;
    Button btnLogin;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        appPref = new AppPreferencias(getApplicationContext());
        uReg = new Registro(appPref);


        btnRegister = (Button) findViewById(R.id.log_register);
        btnRegister.setOnClickListener(this);


        btnLogin= (Button) findViewById(R.id.log_login);
        btnLogin.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.log_username);
        etPassword =  (EditText) findViewById(R.id.log_password);

        etUsername.setText(appPref.getCredenciales(),TextView.BufferType.EDITABLE);

    }

    @Override
    public void onClick(View v) {
        Intent i ;
        String un;
        int id = v.getId();
        switch (id) {
            case R.id.log_register:
                 un= etUsername.getText().toString();
                if (!uReg.ElUsuarioExiste(un)) {
                    uReg.RegistrarUsuario(etUsername.getText().toString());
                    i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                     i = new Intent(this, NoLogActivity.class);
                    startActivity(i);
                    finish();
                }
                break;

            case R.id.log_login:
                un = etUsername.getText().toString();
                if (uReg.ElUsuarioExiste(un)) {
                    i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    i = new Intent(this, NoLogActivity.class);
                    startActivity(i);
                    finish();
                }

                break;
        }
    }
}
