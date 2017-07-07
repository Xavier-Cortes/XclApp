package com.xavicortes.xclapp;

import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import static com.xavicortes.xclapp.MediaPlayerBoundService.EstatMediaPlayerEnum.MARXA;
import static com.xavicortes.xclapp.MediaPlayerBoundService.EstatMediaPlayerEnum.PARAT;
import static com.xavicortes.xclapp.MediaPlayerBoundService.EstatMediaPlayerEnum.PAUSAT;
import static com.xavicortes.xclapp.MediaPlayerBoundService.EstatMediaPlayerEnum.PREPARAT;

/**
 * Created by Xavi on 05/07/2017.
 */

public class MediaPlayerBoundService
        extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener
        {

            public enum EstatMediaPlayerEnum {
                MARXA
                ,PARAT
                ,PAUSAT
                ,PREPARAT
            }

    public int código = 0;

    private final IBinder binder = new MyBinder();
    private int codigo;
    private Context context;
    private MediaPlayer mediaPlayer;

    private EstatMediaPlayerEnum Estat = PARAT;

    public class MyBinder extends Binder {

        public MediaPlayerBoundService getService() {
            return MediaPlayerBoundService.this;
        }

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(context, "Completion", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Toast.makeText(context, "Preapared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext( );
        Toast.makeText(context, "Create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        int i = super.onStartCommand(intent, flags, startId);
        context = getBaseContext();
        Toast.makeText(context, "OnStartCommand", Toast.LENGTH_SHORT).show();
        return i;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //TODO código para liberar recursos
        mediaPlayer.reset();
        mediaPlayer = null;
    }


    public void Ini() throws IOException {
        //Reproducir una cancion de la carpeta raw
        //Toast.makeText(context, "Ini", Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(context,  R.raw.song1);
        Estat = PREPARAT;

    }

    public void Iniciar() throws IOException {
        //Toast.makeText(context, "Ini", Toast.LENGTH_SHORT).show();
        if(mediaPlayer == null) { Ini(); }

         if (Estat != MARXA || Estat == PREPARAT)  {
             mediaPlayer.start();
             Estat = MARXA;
         }
    }
     public void Parar() {
         if(Estat !=PARAT && Estat != PAUSAT) {
             mediaPlayer.stop();
            try {
                 mediaPlayer.prepare();


             } catch (IOException e) {
                 e.printStackTrace();
             }
             Estat = PARAT;
         }
    }
     public void Pausar() {
        if (Estat == MARXA ) {
            mediaPlayer.pause();
            Estat = PAUSAT;
        }
    }


    public EstatMediaPlayerEnum getEstat() {return Estat; }

}