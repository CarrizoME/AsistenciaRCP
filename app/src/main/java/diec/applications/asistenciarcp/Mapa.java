package diec.applications.asistenciarcp;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.aplications.asistenciarcp.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.location.LocationManager.GPS_PROVIDER;



public class Mapa extends AppCompatActivity {

    ArrayList<Marker> marcadores = new ArrayList<>();

    private MapView mapView;
    public MapController mapController;
    public LocationManager locationManager;


    Double LatEmergencia = -0.01;
    Double LongEmergencia = -0.1;
    public Double lat = 0.0, logi = 0.0;
    String HoraActual, Hora = "no paso nada";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        if (tengoPermisoEscritura()) {
            ObtenerData();
            //  startLocationUpdates();
            cargarMapas();
            Localizar();
            OverlayItem a;

        }
    }
//-----------------Arribba bloque de prueba

    //.........................Para abajo funciona.............................................
//Cargamos todos los componente visibles del mapa
    private void cargarMapas() {
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        GeoPoint lugar = new GeoPoint(-38.72, -62.25);
        mapView = (MapView) findViewById(R.id.openmapview);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setCenter(lugar);
        mapController.setZoom(13);
        mapView.setMultiTouchControls(true);



        refrescaPuntos();

    }

    //cargamos los marcadores que iran en el mapa
    public Marker CargarMarcador(GeoPoint posicion, Drawable icono, String titulo, String Subtitulo) {
        Marker marcador = new Marker(mapView);

        marcador.setInfoWindowAnchor(Float.valueOf("0.5"), Float.valueOf("0.8"));

        marcador.setTitle(titulo);
        marcador.setSubDescription(Subtitulo);
        marcador.setIcon(icono);
        marcador.setPosition(posicion);
        return marcador;

    }

    private void refrescaPuntos() {

        // bloque de prueba



        // Añadir un marcador en el mapa
        GeoPoint comedor = new GeoPoint(-38.702785, -62.269485);
        GeoPoint Emergencia = new GeoPoint(LatEmergencia, LongEmergencia);
        GeoPoint Uno=new GeoPoint(-38.7161091,-62.2539199);
        GeoPoint Shopping=new GeoPoint(-38.699909,-62.2413469);
        GeoPoint H20=new GeoPoint(-38.6817811,-62.2616118);
        GeoPoint UnsColon=new GeoPoint(-38.7197661,-62.267567);
        /*
        marcadores.add(CargarMarcador(comedor, this.getResources().getDrawable(R.drawable.marker_dae), "ComedorUNS-Av. Alem 1157", "Pedir DEA en recepcion"));
        marcadores.add(CargarMarcador(Emergencia, this.getResources().getDrawable(R.drawable.marker_emergencia), "OCURRIO UNA EMERGENCIA EN LA ZONA-" + Hora, "Si es posible, acerquese a asistir"));
        marcadores.add(CargarMarcador(H20,this.getResources().getDrawable(R.drawable.marker_dae),"Natatorio H2O - Cuyo 1600","Se entra por cuyo, el DEA esta adentro del complejo, pedir al personal"));
        marcadores.add(CargarMarcador(Shopping,this.getResources().getDrawable(R.drawable.marker_dae),"Shopping - Sarmiento 2153","Se cuenta con 2 DEA, en planta baja y primer piso, estan dentro de un gabinete sobre una columna"));
        marcadores.add(CargarMarcador(Uno,this.getResources().getDrawable(R.drawable.marker_dae),"Uno Bahia Club - Lavalle 605","Pedir el DEA al personal"));
        marcadores.add(CargarMarcador(UnsColon,this.getResources().getDrawable(R.drawable.marker_dae),"UNS Rectorado - Colon80","El DEA se encuentra en planta baja, puede pedirse en mayordomia"));
        */


      //  items.add(new OverlayItem("Comedor UNS- Av. Alem 1157","Pedir en recepcion",Emergencia));
        ArrayList<OverlayItem> Emergencias = new ArrayList<OverlayItem>();
        OverlayItem a=new OverlayItem("Ocurrio una emergencia en la zona","Si es posible acerquese a asistir",Emergencia);
        a.setMarker(this.getResources().getDrawable(R.drawable.marker_emergencia));
        Emergencias.add(a);

        OverlayItem b=new OverlayItem("Ultima posiocion conocida","",new GeoPoint(lat, logi));
        b.setMarker(this.getResources().getDrawable(R.drawable.person));
        Emergencias.add(b);



        //---Marcadores de DEAS------------------------------------------------------------------
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Comedor UNS- Av. Alem 1157","Pedir el DEA en recepcion",comedor));

        items.add(new OverlayItem("Natatorio H2O - Cuyo 1600","Se entra por cuyo, el DEA esta adentro del complejo, pedir al personal",H20));
        items.add(new OverlayItem("Shopping - Sarmiento 2153","Se cuenta con 2 DEA, en planta baja y primer piso, estan dentro de un gabinete sobre una columna",Shopping));
        items.add(new OverlayItem("Uno Bahia Club - Lavalle 605","Pedir el DEA al personal",Uno));
        items.add(new OverlayItem("UNS Rectorado - Colon 80","El DEA se encuentra en planta baja, puede pedirse en mayordomia",UnsColon));

        //------------------------------------------------------------------

        //FIN BLOQUE DE


        mapView.getOverlays().clear();

        Marker TuPosicion = CargarMarcador(new GeoPoint(lat, logi),
                this.getResources().getDrawable(R.drawable.marker_default),"¡HOLA! Esta es tu ultima posicion conocida", "GPS " + HoraActual);
        Marker UnsR = CargarMarcador(UnsColon,
                        this.getResources().getDrawable(R.drawable.marker_dae),"UNS Rectorado - Colon80","El DEA se encuentra en planta baja, puede pedirse en mayordomia");


        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap =
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                        return false;
                    }

                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        return true;
                    }
                };

        //

        ItemizedOverlayWithFocus<OverlayItem> EmeYpos = new ItemizedOverlayWithFocus<OverlayItem>(this, Emergencias, tap);
        EmeYpos.setFocusItemsOnTap(false);
        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(this, items, tap);
        capa.setFocusItemsOnTap(true);

        mapView.getOverlays().add(capa);
        mapView.getOverlays().add(EmeYpos);
    }

    public boolean tengoPermisoEscritura() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }


    private String getDate() {
        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        String date = dfDate.format(Calendar.getInstance().getTime());
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        String time = dfTime.format(Calendar.getInstance().getTime());
        return date + " " + time;
    }


    public void ObtenerData() {

        Context cont = getApplicationContext();
        SharedPreferences sharprefs = getSharedPreferences("ArchivoPOS"
                , MODE_PRIVATE);
        Hora = sharprefs.getString("time"
                , "no hay nada!");
        LatEmergencia = Double.valueOf(sharprefs.getString("lati",
                "0"));
        LongEmergencia = Double.valueOf(sharprefs.getString("longi",
                "0"));
        lat = Double.valueOf(sharprefs.getString("LatiActual", "-10"));
        logi = Double.valueOf(sharprefs.getString("LongiActual", "-12"));
        HoraActual = sharprefs.getString("UltimActualizacion","Todavia no se actualizo tu posición");

    }


//.....................para arriba funciona


    public LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            logi = location.getLongitude();

            Context cont = getApplicationContext();
            SharedPreferences sharprefs = getSharedPreferences("ArchivoPOS"
                    , MODE_PRIVATE);
            HoraActual=getDate();


            SharedPreferences.Editor editData = sharprefs.edit();
            editData.putString("UltimActualizacion",HoraActual);
            editData.putString("LatiActual",lat.toString());
            editData.putString("LongiActual",logi.toString());

            editData.apply();

            refrescaPuntos();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void Localizar() {

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        locationManager.requestLocationUpdates(GPS_PROVIDER, 2000, 0, locListener);
    }
    @Override
    protected void onPause() {

    locationManager.removeUpdates(locListener);



        super.onPause();
    }
}



