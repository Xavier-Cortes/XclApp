package com.xavicortes.xclapp.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.widget.TableLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.xavicortes.xclapp.R;
import com.xavicortes.xclapp.comu.Compartit;
import com.xavicortes.xclapp.comu.FitxaUsuari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**


 */
public class Memory extends Fragment implements View.OnClickListener {


    private int Orientacion;
    private View vw;
    private Intent intent;
    private TableLayout tl;
    private ArrayList<Integer> TaulaImageButton;
    private ArrayList<Serializable> TaulaImatges;
    private Map<Integer,Serializable> TaulaBotoImatge;
    private Integer NumeroPulsadors;
    private Serializable DefaultImage;
    private TextView tvintents;
    private FitxaUsuari fu;


    public Memory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        // força que no es pot modificar la orientació
        Orientacion = getActivity().getRequestedOrientation();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        vw =inflater.inflate(R.layout.memory, container, false);

        // indic que hi hauran menus
        setHasOptionsMenu(true);

        DefaultImage = R.drawable.ic_key;

        // Referencia al tv dels intents
        tvintents = (TextView) vw.findViewById(R.id.Memory_intents);

        // taula de les imatges a mostrar 8 imatges la meitat de 4x4
        TaulaImatges = new ArrayList<Serializable>( Arrays.asList(
                 R.drawable.ic_diamond_ring
                , R.drawable.ic_excalibur
                , R.drawable.ic_gnome
                , R.drawable.ic_magic_wand
                , R.drawable.ic_oak
                , R.drawable.ic_rainbow
                , R.drawable.ic_shield
                , R.drawable.ic_spellbook
        ));

        TaulaImageButton = new ArrayList<Integer> ( Arrays.asList(
                R.id.Memory_ib1, R.id.Memory_ib2, R.id.Memory_ib3, R.id.Memory_ib4
                ,R.id.Memory_ib5, R.id.Memory_ib6, R.id.Memory_ib7, R.id.Memory_ib8
                ,R.id.Memory_ib9, R.id.Memory_ib10, R.id.Memory_ib11, R.id.Memory_ib12
                ,R.id.Memory_ib13, R.id.Memory_ib14, R.id.Memory_ib15, R.id.Memory_ib16


        ));

        // Activacio de la captura del teclat
        for( Integer id: (List<Integer>) TaulaImageButton) {
            ImageButton ib = (ImageButton) vw.findViewById(id);
            ib.setOnClickListener(this);
        }



        CrearNovaPartida();


        fu = Compartit.DadesUsu.getFitxaUsuari();

        return vw;
    }

    void CrearNovaPartida() {

        // inicialitzacio de variables de control del joc
        intents = 0;
        QuantitatParellesAcertades =0 ;
        ib1 = null; ib2=null;
        EstatActual = EstatType.ESPERA;

        tvintents.setText(String.valueOf(intents));

        // Barreja pulsadors
        List<Integer> Pulsadors = new ArrayList<Integer>(TaulaImageButton);
        Collections.shuffle(Pulsadors); // modifica aleatoriament els id dels polsadors

        // Barreja imatges
        List<Serializable> Imatges = new ArrayList<>(TaulaImatges);
        Collections.shuffle(Imatges); // barreja aleatoriament les imatges

        int j = 0;
        // per cada posició de la taula de polsadors barrejada
        for (int i = 0; i < Pulsadors.size(); i+=2,j++) {

            // determina el polsador asignat  a questa posició
            ImageButton ib1 = (ImageButton) vw.findViewById(Pulsadors.get(i));
            //Activa la visibilitat del buto donat que es fa desapareixe en cada acert
            ib1.setVisibility(View.VISIBLE);
            // posa la imatge per defecte per represntar les imatges amagades
            ib1.setImageResource(DefaultImage.hashCode());
            // escala la imatge al tamany del image button
            ib1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // guarda al tag del button el id de la imatge a mostrar
            ib1.setTag(TaulaImatges.get(j).hashCode());

            // analogamant al anterior pero al buto de la posiació i+1
            ImageButton ib2 = (ImageButton) vw.findViewById(Pulsadors.get(i + 1));
            ib2.setVisibility(View.VISIBLE);
            ib2.setImageResource(DefaultImage.hashCode());
            ib2.setScaleType(ImageView.ScaleType.FIT_CENTER);

            ib2.setTag(TaulaImatges.get(j).hashCode());

        }
    }

    @Override
    public void onDestroyView() {
        getActivity().setRequestedOrientation(Orientacion);
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static final int MNU_REINICIAR =1 ;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        getActivity().invalidateOptionsMenu();

        menu.add(Menu.NONE, MNU_REINICIAR, Menu.NONE,"Reiniciar");

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id ) {
           case MNU_REINICIAR:
                CrearNovaPartida();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
    enum EstatType{
        ESPERA
        ,PRIMERA
        ,MOSTRANT
    }

    EstatType EstatActual= EstatType.ESPERA;

    ImageButton ib1;
    ImageButton ib2;
    int QuantitatParellesAcertades = 0;
    int intents =0 ;
    @Override
    public void onClick(View v) {

        // si s'està mostrant no accepta cap polsació
        if (EstatActual == EstatType.MOSTRANT) return;

        int id = v.getId();
        ImageButton ib = (ImageButton) vw.findViewById(id);


        // el boto ha estat pulsat
        if (ib.getTag().hashCode() == -1) return;

        ib.setImageResource(ib.getTag().hashCode());
        ib.setScaleType(ImageView.ScaleType.FIT_CENTER);

        switch (EstatActual) {
            case ESPERA:
                ib1 = ib;
                EstatActual = EstatType.PRIMERA ;
                break;

            case PRIMERA:
                ib2 = ib;
                EstatActual = EstatType.MOSTRANT;

                tvintents.setText(String.valueOf(++intents) );

                boolean acert = false;
                // si hi ha acert
                if (ib1.getTag().hashCode() == ib2.getTag().hashCode()) {
                    QuantitatParellesAcertades++;
                    acert = true;

                }
                //executa la espera per mostrar les fitxes i finaliza al operació en funció de si es un acert o no
                (new Espera()).execute(acert);
                break;
        }
    }

    // Clase `per executar, en un fil diferent de l'activity, la tasca de mantenir les fitxes voltejades
    // i realitzar les operacions pertinets en funció de si es acert o no
    private class Espera extends AsyncTask<Boolean, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... param) {
            try {
                // espera de 3 seg
                sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // pasa el resultat de la selecció
            return param[0];
        }
        @Override
        protected void onPostExecute(Boolean result) {

            if ( result ) {
                // si correcte anula la imatge del boto
                ib1.setImageResource(0); ib2.setImageResource(0);
                ib1.setVisibility(View.INVISIBLE); ib2.setVisibility(View.INVISIBLE);
                // elimian els tags que serviran per indicar que ja el botons ja han estat anulats
                ib1.setTag(-1); ib2.setTag(-1);
                // anula les referencies als últims pulsadors
                ib1 = null; ib2 = null;

                // si s'ha acerat les 8 vegades posibles
                if (QuantitatParellesAcertades==8 ) {
                    try {
                        int res = intents;
                        if(fu.Intents > 0 ) res = Math.min(fu.Intents, intents);
                        fu.setIntents(res);
                        Compartit.DadesUsu.setFitxaUsuari(fu);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MostarDialeg();
                }

            } else {
                // si no es correcte asigna al imatge per defecte
                ib1.setImageResource(DefaultImage.hashCode());
                ib2.setImageResource(DefaultImage.hashCode());

            }
            // torna a posar el estat d'espera
            EstatActual = EstatType.ESPERA;

        }
    }

    // mostra el dialeg de partida finalizada
    public void MostarDialeg() {
        AlertDialog.Builder dialog = new AlertDialog.Builder( getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Partida Finalizada");
        dialog.setMessage("Número de intentos: "+ intents  );
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialog, int id) {
                    CrearNovaPartida();
                }
            });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

}
