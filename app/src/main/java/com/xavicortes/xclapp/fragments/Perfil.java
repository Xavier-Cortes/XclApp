package com.xavicortes.xclapp.fragments;




import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.xavicortes.xclapp.comu.Compartit;
import com.xavicortes.xclapp.comu.DadesUsuari;
import com.xavicortes.xclapp.comu.FitxaUsuari;
import com.xavicortes.xclapp.LoginActivity;
import com.xavicortes.xclapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static android.app.Activity.RESULT_OK;


/*
    Mostra una fitxa de les dades del usuari actiu
 */
public class Perfil extends Fragment
        implements View.OnClickListener {

    View vw;

    // View osn mostar la informació
    private EditText etNom;
    private EditText etContrasenya;
    private EditText etAdreça;
    private EditText etNotificacions;
    private TextView tvIntents;
    private ImageButton ibImatge;
    private Uri imgUri;
    private Button btLogout;
    private FitxaUsuari fu;
    private HashMap<View,Field> TaulaControlsCamps;
    private int Orientacion;

    private LocationManager locationManager;
    private TextView longitudeValueBest;
    private TextView latitudeValueBest;
    private TextView longitudeValueNetwork;
    private TextView latitudeValueNetwork;
    private TextView longitudeValueGPS;
    private TextView latitudeValueGPS;

    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);


        // orientacio bloquejada
        Orientacion = getActivity().getRequestedOrientation();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        vw = inflater.inflate(R.layout.perfil, container, false);

       // Base de dades
        fu  = Compartit.DadesUsu.getFitxaUsuari();


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        // location managet
        etNom = (EditText) vw.findViewById(R.id.Perfil_edNom);
        etContrasenya = (EditText) vw.findViewById(R.id.Perfil_edContrasenya);
        etAdreça = (EditText) vw.findViewById(R.id.Perfil_edAdreça);
        etNotificacions = (EditText) vw.findViewById(R.id.Perfil_edNotificacions);
        tvIntents = (TextView) vw.findViewById(R.id.Perfil_tvIntents);
        ibImatge = (ImageButton)vw.findViewById(R.id.Perfil_Imatge);


        TaulaControlsCamps = new HashMap<>();

        // faig servir reflexio per guardar una referencia al camps de la fitxa de usuari
        // i així poder carregar les dades a la pantalla i per guardar-les
        try {
            TaulaControlsCamps.put(etNom, FitxaUsuari.class.getField("Nom"));
            TaulaControlsCamps.put(etContrasenya, FitxaUsuari.class.getField("Contrasenya"));
            TaulaControlsCamps.put(etAdreça, FitxaUsuari.class.getField("Adreça"));
            TaulaControlsCamps.put(etNotificacions, FitxaUsuari.class.getField("Notificacions"));
            TaulaControlsCamps.put(tvIntents, FitxaUsuari.class.getField("Intents"));
            TaulaControlsCamps.put(ibImatge, FitxaUsuari.class.getField("Imatge"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // activacio carrega de dades i configuracio dels views
        Set TKeysD = TaulaControlsCamps.keySet();
        for( View vista : (Collection<View>)  TKeysD) {
            Field  fld = TaulaControlsCamps.get(vista);
            if (vista.getClass().getSimpleName().equals(AppCompatImageButton.class.getSimpleName())) {

                ((ImageButton) vista).setOnClickListener(this);
                try {
                    ((ImageButton) vista).setImageBitmap(decodeBase64(fu.getImatge()));
                } catch ( Exception ex ) {

                }

            } else if (vista.getClass().getSimpleName().equals(AppCompatTextView.class.getSimpleName())) {

                try {
                    ((TextView) vista).setText(fld.get(fu).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            } else if (vista.getClass().getSimpleName().equals(AppCompatEditText.class.getSimpleName())) {

                try {
                    ((EditText) vista).setText(fld.get(fu).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        btLogout = (Button) vw.findViewById(R.id.Perfil_btLogout);
        btLogout.setOnClickListener(this);

        return vw;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    private static final int PETICION1 = 1;

    @Override
    public void onClick(View v) {
        Intent i;
        int id;
        id = v.getId();
        switch (id)  {
            case R.id.Perfil_btLogout:
                i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                break;

            case R.id.Perfil_Imatge:

                Intent getImageAsContent = new Intent(Intent.ACTION_GET_CONTENT, null);
                getImageAsContent.setType("image/*");
                startActivityForResult(getImageAsContent, PETICION1);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == PETICION1){
                imgUri = data.getData();
                try {
                   // recupera la imatge selecionada
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(),
                            imgUri);
                    // Es convertix a string per poder guardar a la base de dades
                    fu.setImatge( encodeTobase64(bmp));
                    // s'assigna al control
                    ibImatge.setImageBitmap(bmp);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Converteix un bitmap a String
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    // Converteix un string a Bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,      decodedByte.length);
    }

    @Override
    public void onDestroyView() {

        // activacio de la captura del teclat
        Set TKeysD = TaulaControlsCamps.keySet();
        for( View vista : (Collection<View>)  TKeysD) {
            if (vista.getClass().getSimpleName().equals(AppCompatImageButton.class.getSimpleName()) ) {
                // la imatge ja està carregada a la fitxa

            } else if (vista.getClass().getSimpleName().equals(AppCompatTextView.class.getSimpleName()) ) {


            } else if (vista.getClass().getSimpleName().equals(AppCompatEditText.class.getSimpleName()) ) {
                // Carrega les dades a la fitxa de de usuario
                Field  fld = TaulaControlsCamps.get(vista);
                try {
                    fld.set(fu,((EditText) vista).getText().toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }

        }

        // guarda les dades de usuari a la base de dades
        DadesUsuari.setFitxaUsuari(fu);
        getActivity().setRequestedOrientation(Orientacion);
        super.onDestroyView();
    }


   /* double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleGPSUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText(R.string.resume);
        } else {
            if (getActivity().checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && getActivity().checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);
            button.setText(R.string.pause);
        }
    }

    public void toggleBestUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            if (Activity.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.removeUpdates(locationListenerBest);
            button.setText(R.string.resume);
        } else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, true);
            if (provider != null) {
                locationManager.requestLocationUpdates(provider, 2 * 20 * 1000, 10, locationListenerBest);
                button.setText(R.string.pause);
                Toast.makeText(getActivity(), "Best Provider is " + provider, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void toggleNetworkUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            if (Activity.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.resume);
        }
        else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 20 * 1000, 10, locationListenerNetwork);
            Toast.makeText(getActivity(), "Network provider started running", Toast.LENGTH_LONG).show();
            button.setText(R.string.pause);
        }
    }


    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueBest.setText(longitudeBest + "");
                    latitudeValueBest.setText(latitudeBest + "");
                    Toast.makeText( getActivity(), "Best Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };


    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueNetwork.setText(longitudeNetwork + "");
                    latitudeValueNetwork.setText(latitudeNetwork + "");
                    Toast.makeText(getActivity(), "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };



    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueGPS.setText(longitudeGPS + "");
                    latitudeValueGPS.setText(latitudeGPS + "");
                    Toast.makeText(getActivity(), "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };*/
}
