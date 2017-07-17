package com.xavicortes.xclapp.Util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xavicortes.xclapp.R;
import com.xavicortes.xclapp.comu.FitxaUsuari;

import java.util.ArrayList;


public class LlistaClassificacioAdapter extends RecyclerView.Adapter<LlistaClassificacioAdapter.AdapterViewHolder>{

    Context context;
    ArrayList<FitxaUsuari> FitxesUsuari;

    public LlistaClassificacioAdapter(Context context, ArrayList<FitxaUsuari> contactos){
        this.context = context;
        this.FitxesUsuari = contactos;

    }

    @Override
    public LlistaClassificacioAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.filaclassificacio, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LlistaClassificacioAdapter.AdapterViewHolder adapterViewholder, int position) {
        if ((position >= 0) && (position <= 2)) {
            adapterViewholder.icon.setImageDrawable(adapterViewholder.v.getResources()
                    .getDrawable(android.R.drawable.btn_star_big_on));
        } else {
           adapterViewholder.icon.setImageResource(0);

        }

        adapterViewholder.nom.setText(FitxesUsuari.get(position).getNom());
        adapterViewholder.intents.setText(String.valueOf(FitxesUsuari.get(position).getIntents()));
    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return FitxesUsuari.size();
    }
    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public ImageView icon;
        public TextView nom;
        public TextView intents;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.icon = (ImageView) itemView.findViewById(R.id.Fila_Icon);
            this.nom = (TextView) itemView.findViewById(R.id.Fila_Nom);
            this.intents = (TextView) itemView.findViewById(R.id.Fila_Intents);
        }
    }



}
