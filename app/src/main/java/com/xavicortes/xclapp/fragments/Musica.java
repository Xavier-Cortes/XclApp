package com.xavicortes.xclapp.fragments;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xavicortes.xclapp.comu.Compartit;
import com.xavicortes.xclapp.Util.MediaPlayerBoundService;
import com.xavicortes.xclapp.R;

import java.io.IOException;

import static android.content.Context.BIND_AUTO_CREATE;

/*
Controla un boudservice on hi ha un mediaplayer executant una canço
*/
public class Musica
        extends Fragment
        implements View.OnClickListener {

    private View vw;  //referencia al layout/vista

    // Referencias a los pulsadores
     private Button btstart;
     private Button btstop;
     private Button btpause;

     private Intent intent;   // referencia al ??
     private boolean startbs; // indica que se ha iniciado el boundservice
     private int Orientacion;


    public Musica() {
       // Required empty public constructor

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        Orientacion = getActivity().getRequestedOrientation();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        vw = inflater.inflate(R.layout.musica, container,  false);

        // referències al polsadors
        btstart = (Button) vw.findViewById(R.id.btMarcha);
        btstop = (Button) vw.findViewById(R.id.btparada);
        btpause = (Button) vw.findViewById(R.id.btpausa);

        // activar rebre polsacions
        btstart.setOnClickListener(this);
        btstop.setOnClickListener(this);
        btpause.setOnClickListener(this);

        // referencia al servei a executar
        intent = new Intent(Compartit.context, MediaPlayerBoundService.class);

        return vw;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(context, "CLICKED BT start", Toast.LENGTH_SHORT).show();
        // Execució del lligam amb el servei amb la opció de auto_creació i manteniment mentre existegi un vincle amb algún client
        startbs =Compartit.context.bindService(intent, connection, BIND_AUTO_CREATE);
        if ( !startbs){Compartit.context.unbindService(connection);}

        MostrarEstat();

    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(context, "CLICKED BT Stop", Toast.LENGTH_SHORT).show();
        if ( !startbs) {
            bService.stopService(intent);
            Compartit.context.unbindService(connection);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(context, "CLICKED BT Destroy", Toast.LENGTH_SHORT).show();
        // para para el servei i elimian el lligam s'espera que el service es destruexi al no tenir cap client
        if ( !startbs){
            bService.stopService(intent);
            Compartit.context.unbindService(connection);
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Button  btn = null;
        switch(view.getId()) {
            case R.id.btMarcha:
                //Toast.makeText(context, "CLICKED BT MARCHA", Toast.LENGTH_SHORT).show();
                try {
                    bService.Iniciar();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btparada:
                //Toast.makeText(context, "CLICKED BT PARADA", Toast.LENGTH_SHORT).show();
                btn = (Button) view.findViewById(view.getId());
                bService.Parar();
                break;

            case R.id.btpausa:
                //Toast.makeText(context, "CLICKED BT BTPAUSA", Toast.LENGTH_SHORT).show();
                bService.Pausar();
                break;
        }
        MostrarEstat();

    }

    void MostrarEstat() {
        // si no s'ha creat el servei retorna
        if(bService== null) return;

        if(bService.getEstat() == MediaPlayerBoundService.EstatMediaPlayerEnum.MARXA ) {
            btstart.setTypeface(null,Typeface.BOLD);
            btstop.setTypeface(null,Typeface.NORMAL);
            btpause.setTypeface(null,Typeface.NORMAL);
        }

        if(bService.getEstat() == MediaPlayerBoundService.EstatMediaPlayerEnum.PARAT ) {
            btstart.setTypeface(null,Typeface.NORMAL);
            btstop.setTypeface(null,Typeface.BOLD);
            btpause.setTypeface(null,Typeface.NORMAL);
        }

        if(bService.getEstat() == MediaPlayerBoundService.EstatMediaPlayerEnum.PAUSAT ) {
            btstart.setTypeface(null,Typeface.NORMAL);
            btstop.setTypeface(null,Typeface.NORMAL);
            btpause.setTypeface(null,Typeface.BOLD);
        }
    }

    private boolean bound;
    private MediaPlayerBoundService bService;

    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder){
            MediaPlayerBoundService.MyBinder mibinder = (MediaPlayerBoundService.MyBinder) binder;
            bService = mibinder.getService();
            bound = true;
            //Toast.makeText(context, "Onservice connected", Toast.LENGTH_SHORT).show();
            MostrarEstat();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0){
            bound = false;
            bService = null;
            //Toast.makeText(context, "Onservice disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        Compartit.AppPref.setEstadoMediaplayer( MediaPlayerBoundService.EstatMediaPlayerEnum.ToString(bService.getEstat()));
        getActivity().setRequestedOrientation(Orientacion);
        super.onDestroyView();
    }
}
