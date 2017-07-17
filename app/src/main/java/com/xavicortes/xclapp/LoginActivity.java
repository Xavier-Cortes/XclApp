package com.xavicortes.xclapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.xavicortes.xclapp.comu.Compartit;
import com.xavicortes.xclapp.comu.FitxaUsuari;

// Demana la identificació del usuari o be si es vol registrar
public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    Button btnRegister;
    Button btnLogin;
    EditText etUsername;
    EditText etContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        btnRegister = (Button) findViewById(R.id.log_register);
        btnRegister.setOnClickListener(this);


        btnLogin= (Button) findViewById(R.id.log_login);
        btnLogin.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.log_username);
        etContrasenya =  (EditText) findViewById(R.id.log_password);


        //DadesUsu.DB.EliminarTabla();
        //DadesUsu.DB.CrearTabla();

        // intenta carregat el nom del usuari últim
        // si falla perque es la primera vegada no fa res
        /*try {
            etUsername.setText(Compartit.DadesUsu.getFitxaUsuari().getNom(),TextView.BufferType.EDITABLE);

        } catch ( Exception e) {

        }*/

        // al pasar per aquesta activity s'anulen les identificacions anotades
        Compartit.AppPref.setPrimerVegada(true);
    }

    @Override
    public void onClick(View v) {
        Intent i ;
        String username;
        int id = v.getId();
        boolean err =false;
        switch (id) {
            case R.id.log_register:
                username= etUsername.getText().toString();
                if (!Compartit.DadesUsu.ElUsuarioExiste(username)) {
                    if ( etContrasenya.getText().toString().equals(""))
                    {
                        err = true;
                    } else {
                        Compartit.DadesUsu.RegistrarUsuario(username, etContrasenya.getText().toString());
                        i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else {
                     err = true;
                }
                break;

            case R.id.log_login:
                if (Compartit.DadesUsu.ComprovarCredencials(etUsername.getText().toString(),etContrasenya.getText().toString())) {
                    Compartit.DadesUsu.ActivarUsuari(etUsername.getText().toString());
                    i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    err = true;
                }
                break;
        }

        if (err) {
            i = new Intent(this, NoLogActivity.class);
            startActivity(i);
            finish();
        }
    }
}
