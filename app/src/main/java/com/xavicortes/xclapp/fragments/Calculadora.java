package com.xavicortes.xclapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.xavicortes.xclapp.Util.CalcularExpressions;
import com.xavicortes.xclapp.comu.DadesUsuari;
import com.xavicortes.xclapp.comu.FitxaUsuari;
import com.xavicortes.xclapp.R;
import com.xavicortes.xclapp.comu.Compartit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Calculadora
    extends Fragment
    implements View.OnClickListener {

    String TAG = "calculadora";
    private String ValorActual = "";
    private String Expressio = "";
    private double ans = 0.0;
    private Context context;
    private HashMap<Button, String> TaulaTeclat;

    private TextView tvResultat;
    private TextView tvExpresio;
    private ImageButton buttonOne;
    private View vw;
    private FitxaUsuari fu;
    private int Orientacion;


    public Calculadora() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        Orientacion = getActivity().getRequestedOrientation();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        setHasOptionsMenu(true);

        vw = inflater.inflate( R.layout.calculadora, container, false);

        context =vw.getContext();


        // Definicaió de les tecles a fer servir
        TaulaTeclat = new HashMap();
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt0), "0");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt1), "1");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt2), "2");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt3), "3");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt4), "4");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt5), "5");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt6), "6");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt7), "7");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt8), "8");
        TaulaTeclat.put((Button) vw.findViewById(R.id.bt9), "9");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btComa), ".");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btMas), "+");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btMenos), "-");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btMult), "x");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btDiv), "/");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btPorCent), "%");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btIgual), "=");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btAC), "AC");
        TaulaTeclat.put((Button) vw.findViewById(R.id.btAns), "ans");


        // activacio de la captura del teclat
        Set TKeysD = TaulaTeclat.keySet();
        for( Button ib: (Collection<Button>)  TKeysD) {
            ib.setOnClickListener(this);
        }

        // captura dels textview del layout
        tvExpresio = (TextView) vw.findViewById(R.id.twExpresio);
        tvResultat = (TextView) vw.findViewById(R.id.twResultat);

        // carrega de las preferences
        ans = Double.valueOf(Compartit.AppPref.getAns());
        Expressio = Compartit.AppPref.getExpresio();
        tvExpresio.setText(Expressio );
        tvResultat.setText(Compartit.AppPref.getResultat());


        // retorn del viewer on s'ha associat el fragment
        return vw;
}

    @Override
    public void onClick(View v) {
        if (v == null) return;

        String s = TaulaTeclat.get((Button) vw.findViewById(v.getId()));
        switch (s) {
            case "=":
                try {
                    ans = CalcularExpressions.CalcularExpressio(Expressio, ans);

                    // compueba que no hoi hagi una divisió per zero, donat que amb double no executa una excepció
                    if (ans == Double.POSITIVE_INFINITY) {
                        throw new ArithmeticException("División por cero");
                    } else if (ans == Double.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("División por cero");
                    }

                    tvResultat.setText(Double.toString(ans));

                } catch (ArithmeticException e) {
                    tvResultat.setText(e.getMessage());

                } catch (Exception e) {
                    tvResultat.setText("Error en la expresion");

                }
                break;

            case "AC":
                Expressio = "";
                tvResultat.setText(Double.toString(0));
                tvExpresio.setText(Expressio);
                break;


            default:
                Expressio = Expressio + s;
                tvExpresio.setText(Expressio);

        }
    }

    @Override
    public void onDestroyView() {
        if (Compartit.AppPref != null) {
            Compartit.AppPref.setAns(Double.toString(ans));
            Compartit.AppPref.setExpressio(Expressio);
            Compartit.AppPref.setResultat(tvResultat.getText().toString());
        }

        getActivity().setRequestedOrientation(Orientacion);

        //Toast.makeText(context, "Destruido", Toast.LENGTH_SHORT).show();

        super.onDestroyView();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static final int MNU_NOTIFICACIONES = 1;
    private static final int SBMNU_TOAST = 2;
    private static final int SBMNU_Estado = 3;
    private static final int MNU_LLAMAR =4 ;
    private static final int MNU_NAVEGADOR =5 ;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        // resertea los menus
        getActivity().invalidateOptionsMenu();

        // crea el menus especifico del fragment
        SubMenu smnu = menu.
                addSubMenu(Menu.NONE, MNU_NOTIFICACIONES, Menu.NONE, "Notificaciones")
                .setIcon(android.R.drawable.ic_menu_agenda);
        smnu.add(Menu.NONE, SBMNU_TOAST, Menu.NONE, "Toast");
        smnu.add(Menu.NONE, SBMNU_Estado, Menu.NONE, "Estado");

        menu.add(Menu.NONE, MNU_LLAMAR, Menu.NONE,"Llamar");
        menu.add(Menu.NONE, MNU_NAVEGADOR, Menu.NONE,"Navegador");

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id ) {
            case SBMNU_TOAST:
                fu = DadesUsuari.getFitxaUsuari();
                fu.setNotificacions("TOAST");
                DadesUsuari.setFitxaUsuari(fu);
                return true;

            case SBMNU_Estado:
                fu = DadesUsuari.getFitxaUsuari();
                fu.setNotificacions("ESTADO");
                DadesUsuari.setFitxaUsuari(fu);
                return true;

            case MNU_LLAMAR:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvExpresio.getText().toString()));
                startActivity(intent);
                return true;

            case MNU_NAVEGADOR:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

}
