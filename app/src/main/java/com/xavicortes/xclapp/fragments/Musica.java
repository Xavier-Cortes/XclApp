package com.xavicortes.xclapp.fragments;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xavicortes.xclapp.MediaPlayerBoundService;
import com.xavicortes.xclapp.R;

import java.io.IOException;
import android.support.v4.app.Fragment;
import android.widget.Toast;


import com.xavicortes.xclapp.R;
import static android.content.Context.BIND_AUTO_CREATE;

/**
 * A simple {@link Fragment} subclass.
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
     private Context context; // refrencia a la activity
     private boolean startbs; // indica que se ha iniciado el boundservice
     private boolean Preparado;


   public Musica() {
       // Required empty public constructor

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        vw = inflater.inflate(R.layout.musica, container,  false);

        btstart = (Button) vw.findViewById(R.id.btMarcha);
        btstop = (Button) vw.findViewById(R.id.btparada);
        btpause = (Button) vw.findViewById(R.id.btpausa);

        btstart.setOnClickListener(this);
        btstop.setOnClickListener(this);
        btpause.setOnClickListener(this);
        
        context = this.getContext();
        intent = new Intent(context, MediaPlayerBoundService.class);
        return vw;
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(context, "CLICKED BT start", Toast.LENGTH_SHORT).show();
        startbs =context.bindService(intent, connection, BIND_AUTO_CREATE);
        if ( !startbs){context.unbindService(connection);}

    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(context, "CLICKED BT Stop", Toast.LENGTH_SHORT).show();
        if ( !startbs){context.unbindService(connection);}

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(context, "CLICKED BT Destroy", Toast.LENGTH_SHORT).show();
        if ( !startbs){context.unbindService(connection);}

    }

    static int  TYPEFACE_NORMAL = 0;
    static int  TYPEFACE_BOLD = 1;
    static int TYPEFACE_ITALIC = 2;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Button  btn = null;
        switch(view.getId()) {
            case R.id.btMarcha:
                Toast.makeText(context, "CLICKED BT MARCHA", Toast.LENGTH_SHORT).show();
                try {
                    bService.Iniciar();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btparada:
                Toast.makeText(context, "CLICKED BT PARADA", Toast.LENGTH_SHORT).show();
                btn = (Button) view.findViewById(view.getId());
                bService.Parar();
                break;

            case R.id.btpausa:
                Toast.makeText(context, "CLICKED BT BTPAUSA", Toast.LENGTH_SHORT).show();
                bService.Pausar();
                break;
        }

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
            Toast.makeText(context, "Onservice connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0){
            bound = false;
            Toast.makeText(context, "Onservice disconnected", Toast.LENGTH_SHORT).show();
        }
    };



}
