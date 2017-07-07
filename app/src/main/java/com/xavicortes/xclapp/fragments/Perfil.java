package com.xavicortes.xclapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.xavicortes.xclapp.AppPreferencias;

import com.xavicortes.xclapp.LoginActivity;
import com.xavicortes.xclapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment implements View.OnClickListener {

    View vw;
    AppPreferencias appPref;
    private Button btnLogout;

    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vw = inflater.inflate(R.layout.perfil, container, false);

        appPref= new AppPreferencias(getContext().getApplicationContext());
        btnLogout = (Button) vw.findViewById(R.id.Perfil_btlogout);
        btnLogout.setOnClickListener(this);


        return vw;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        int id;
        id = v.getId();
        switch (id) {
            case R.id.Perfil_btlogout:
                appPref.setPrimerVegada(false);

                i  = new Intent(getActivity(),  LoginActivity.class);
                startActivity(i);

                getActivity().finish();
                break;



        }


    }
}
