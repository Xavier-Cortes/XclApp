package com.xavicortes.xclapp.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.*;

import com.xavicortes.xclapp.Operacio;
import com.xavicortes.xclapp.R;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calculadora extends Fragment
      implements View.OnClickListener {

        String TAG = "calculadora";
        private String ValorActual = "";
        private String ValorEntrada = "";
        private boolean DecimalActiu = false;
        private boolean NouNumero = true;
        private Operacio OpActual = null;
        private Context context;
        private HashMap<Button, Integer> TaulaDigits;

        //private ArrayMap<ImageButton, com.xavicortes.calculadora.Operacio.OperacioEnum> TaulaOperacions;
        private TextView tv;
        private ImageButton buttonOne;
        private View vw;

    public Calculadora() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vw = inflater.inflate(R.layout.calculadora, container, false);

        context =vw.getContext();

        //Toast.makeText(context, "CreateView", Toast.LENGTH_SHORT).show();


    TaulaDigits = new HashMap();
        TaulaDigits.put((Button) vw.findViewById(R.id.bt0), 0);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt1), 1);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt2), 2);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt3), 3);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt4), 4);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt5), 5);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt6), 6);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt7), 7);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt8), 8);
        TaulaDigits.put((Button) vw.findViewById(R.id.bt9), 9);
        TaulaDigits.put((Button) vw.findViewById(R.id.btComa), -1);

    //ImageButton[] Keys = (ImageButton[]) TaulaDigits.keySet().toArray();
    Set TKeysD = TaulaDigits.keySet();
        for( Button ib: (Collection<Button>)  TKeysD) {
        ib.setOnClickListener(this);
    }

    Operacio.OperacioEnum[] TKeysO = Operacio.OperacioEnum.values();
        for( Operacio.OperacioEnum ope: TKeysO) {
        ((Button) vw.findViewById(ope.getId())).setOnClickListener(this);
    }

    tv = (TextView) vw.findViewById(R.id.tw);

    return vw;
}


    @Override
    public void onClick(View v) {
        if (v == null) return;

        Integer val = TaulaDigits.get((Button) vw.findViewById(v.getId()));
        if (val != null) {
            if( val == -1 ) {
                if (!DecimalActiu) {DecimalActiu = true;ValorEntrada += ",";}
            } else {
                ValorEntrada += val;
            }
            tv.setText(ValorEntrada);

        } else {
            ValorEntrada = "";
            OpActual = new Operacio(Double.parseDouble(tv.getText().toString()), v.getId(), OpActual);
            try {

                DecimalFormat df = new DecimalFormat("#.#######");

                Double d = OpActual.CalcularOp();
                String s = df.format(d);

                ValorActual = String.valueOf(d);

                if (OpActual.getOpe().Prioritat == Operacio.PrioritatOpEnum.Resultat) {
                    OpActual = null;
                } else {


                }
                tv.setText(ValorActual);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

}
