
package diec.applications.asistenciarcp;


import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.Manifest;
import android.content.Context;

import android.media.AudioManager;
import android.media.SoundPool;

import android.widget.Button;
import android.widget.Toast;


import com.aplications.asistenciarcp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.location.LocationManager.GPS_PROVIDER;



public class EmergenciaASISTENCIaRCP extends AppCompatActivity {

    public DatabaseReference reference;

    private Double Longitud=0.0;
    private Double Latitud=0.0;
    public Boolean Reproduciendo=false,Vibrando=false,SeAlerto=false;;
    public String Tiempo;
    public SoundPool ritmo;
    public Button play,vibraciones;
    Vibrator vibra;
    long [] patron= {400, 145, 400, 145, 400,145,400, 145, 400, 145, 400,145, 400, 145, 400,145, 400, 145, 400,145, 400, 145, 400,145, 400, 145, 400,145, 400, 145  };
    public LocationManager locationManager;



    int sonido;
    public Integer cont=1;




    public LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Double lati=location.getLatitude();
            Double longi=location.getLongitude();
            GuardarDatos(lati,longi);


            if(!SeAlerto) {
                if (cont%3==0) {
                    Toast.makeText(EmergenciaASISTENCIaRCP.this,"se actualizo la posicion "+cont.toString(),Toast.LENGTH_SHORT).show();
                    if (HayInternet()) {
                        EscribirDatos();
                        EscribirDatos();
                        SeAlerto=true;
                        Toast.makeText(EmergenciaASISTENCIaRCP.this, "Se alerto de su emergencia a otros usuarios", Toast.LENGTH_SHORT).show();
                        locationManager.removeUpdates(locListener);
                    }
                }
                cont = cont + 1;
            }
            if (cont>30){
                locationManager.removeUpdates(locListener);

            }
        }
        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.
                activity_emergencia_asistencia_rcp);


        //.................VOLUME
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ritmo=new SoundPool(1,
                AudioManager.STREAM_MUSIC,1);
        sonido = ritmo.load(this,R.raw.sound_rcp,
                1);
        play=(Button)findViewById(R.id.botonPlay);

        //.............vibraciones
        vibraciones=(Button)findViewById(R.id.buttonVibr);
        vibra = (Vibrator)
                getSystemService(VIBRATOR_SERVICE);

        ObtenerLocalizacion();



    }


    public void ReproducirSonido(View view){
        if (!Reproduciendo){
            vibra.cancel();
            ritmo.play(sonido, 1, 1, 1, -1, 0);
           ritmo.autoResume();
           play.setText("  Parar sonido ");
            vibraciones.setText(" Comenzar vibraciones ");
           Reproduciendo=true;
           Vibrando=false;
        }else{
           ritmo.autoPause();
           play.setText("  Reproducir sonido  ");
           Reproduciendo=false;
       }
    }
    public void ComenzarVibraciones(View view){
        if (!Vibrando){
            ritmo.autoPause();
            vibra.vibrate(patron,0);
            vibraciones.setText("  Parar vibraciones ");
            play.setText("  Reproducir sonido  ");
            Vibrando=true;
            Reproduciendo=false;
        }else{

            vibraciones.setText(" Comenzar vibraciones ");
            vibra.cancel();
            Vibrando=false;
        }
    }



    @Override
    protected void onDestroy()
    {

        ritmo.autoPause();
        vibra.cancel();
        super.onDestroy();

    }




    //...DEVUELVE UN StRING CON LAS FECHA Y HORA "2019/02/30 14:20"





    public void EscribirDatos(){
        //..Si entra a esta instancia escribe su posicion en el mapa a traves del DATABASE
        String fecha="0:0";
        reference= FirebaseDatabase.getInstance().getReference("Posicion");

        Context cont=getApplicationContext();
        SharedPreferences sharprefs = getSharedPreferences("ArchivoPOS",MODE_PRIVATE);
        String tok=sharprefs.getString("CualEsMITOken","no hay nada!");
        Latitud= Double.valueOf(sharprefs.getString("LatiActual","-10"));
        Longitud= Double.valueOf(sharprefs.getString("LongiActual","-10"));
        fecha=sharprefs.getString("lasTime","0:0");
        PosicionDatabase Dato= new PosicionDatabase(Latitud,Longitud,fecha,tok);

        reference.push().setValue(Dato);
    }


    public boolean HayInternet(){

        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {
                tipoConexion1 = true;
            }
            if (mMobile.isConnected()) {
                tipoConexion2 = true;
            }

            if (tipoConexion1 == true || tipoConexion2 == true) {
                return true;
            }
        }
        else {
            return false;
        }
        return  false;
    }


    public void ObtenerLocalizacion(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
        }

        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
      /*    Location location;

        location= locationManager.getLastKnownLocation(GPS_PROVIDER);   //obtengo una posicion rapido!
        if(location==null){
            location=locationManager.getLastKnownLocation(NETWORK_PROVIDER);
            if(location!=null){
                TengoPosicion=true;
            }

        }else {
            TengoPosicion=true;
        }


        if(TengoPosicion) {
            lat = locationManager.getLastKnownLocation(NETWORK_PROVIDER).getLatitude();
            logi = locationManager.getLastKnownLocation(NETWORK_PROVIDER).getLongitude();
            GuardarDatos(lat,logi);// me permite obtener una posicion rapida
        }
       // locationManager.requestSingleUpdate(NETWORK_PROVIDER, locListener,null);
        */
        locationManager.requestLocationUpdates(GPS_PROVIDER, 2000, 0, locListener);


    }


    void GuardarDatos(Double latitud,Double longitud){

        String SLatitud=latitud.toString();
        String SLongitud=longitud.toString();

        Context cont=getApplicationContext();
        SharedPreferences sharprefs = cont.
                getSharedPreferences("ArchivoPOS",
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editData = sharprefs.edit();
        editData.putString("lasTime",getDate());
        editData.putString("LatiActual",SLatitud);
        editData.putString("LongiActual",SLongitud);

        editData.apply();
        editData.commit();


    }

    private String getDate(){
        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        String date=dfDate.format(Calendar.getInstance().getTime());
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        String time = dfTime.format(Calendar.getInstance().getTime());
        return date + " " + time; }


}







