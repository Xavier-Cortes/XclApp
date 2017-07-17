package com.xavicortes.xclapp.fragments;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xavicortes.xclapp.comu.Compartit;
import com.xavicortes.xclapp.comu.DadesUsuari;
import com.xavicortes.xclapp.comu.FitxaUsuari;
import com.xavicortes.xclapp.Util.LlistaClassificacioAdapter;
import com.xavicortes.xclapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Classificacio extends Fragment {


    private View vw;
    //private Context context;
   // private AppPreferencies appPref;
   // private DadesUsu DadesUsu;
    private ArrayList<FitxaUsuari> alfu;
    private LinearLayoutManager mLinearLayout;
    private RecyclerView rvwClassificacio;
    private int Orientacion;

    public Classificacio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        Orientacion = getActivity().getRequestedOrientation();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        vw =  inflater.inflate(R.layout.classificacio, container, false);


        //appPref = new AppPreferencies(context);

        //DadesUsu = new DadesUsu(context);

        //findViewById del layout activity_main
        rvwClassificacio = (RecyclerView) vw.findViewById(R.id.Classificacio_rvwLLista);


        mLinearLayout = new LinearLayoutManager(vw.getContext());

        //Asignamos el LinearLayoutManager al recycler:
        rvwClassificacio.setLayoutManager(mLinearLayout);


        LlistaClassificacioAdapter adpLlistaClassificacio = initAdapterData();


        rvwClassificacio.setAdapter(adpLlistaClassificacio);

        return vw;
    }



    private ArrayList<FitxaUsuari> getDadesLlistaClassificacio()  {
       return DadesUsuari.ClassificacioUsuaris();
    }

    private LlistaClassificacioAdapter initAdapterData () {
        ArrayList<FitxaUsuari>  al =getDadesLlistaClassificacio();
        return new LlistaClassificacioAdapter(vw.getContext(),  al);
    }

    @Override
    public void onDestroyView() {
        getActivity().setRequestedOrientation(Orientacion);
        super.onDestroyView();
    }
}
