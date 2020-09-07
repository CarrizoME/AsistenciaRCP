package diec.applications.asistenciarcp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aplications.asistenciarcp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PedirPERMISOS();
        createNotificationChannel();

    }

    public void EsUnaEmergencia(View a){
        Intent otra= new Intent(this,EmergenciaPrimeraParte.class);
        startActivity(otra);
    }
    public void RecordarProcedimiento(View Coso){
        Intent otra= new Intent(this,RecordaRCP.class);
        startActivity(otra);
    }
    public void BotonTest(View Coso){
        Intent otra=new Intent(this,Mapa.class);
        startActivity(otra);
    }
    public void BotonConfig(View Coso){
        Intent otra=new Intent(this, Configuracion.class);
        startActivity(otra);
    }


//..............PERMISOS
    private void PedirPERMISOS(){
        Context cont=getApplicationContext();
        SharedPreferences sharprefs = getSharedPreferences("ArchivoPOS"
                ,MODE_PRIVATE);



        if((sharprefs.getString("SubiToken","false").equals("false"))){  // Cuando se inicia el valor inicial es falso y cuando se ejecuta se cambia a true y no se vuelve ejecutar nunca mas!

            obtenerToken();
            SharedPreferences.Editor editor =sharprefs.edit();
            editor.putString("SubiToken","true");
            editor.apply();
            editor.commit();
        }

        // chequeo que la version sea la necesaria para pedir permisos, en versiones anteriores no se precisa pedir permisos.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //permisos de POSICION
            if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (checkSelfPermission( Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                    &&(checkSelfPermission( Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
                    &&(checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) ){
            }
            else {
                Toast.makeText(this,"Se subio el token",Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,//permiso de Gps
                        Manifest.permission.ACCESS_COARSE_LOCATION,//permiso de ubicacion
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//permiso de escritura
                        Manifest.permission.INTERNET,//permiso de uso de Internet
                        Manifest.permission.CALL_PHONE,//permiso de llamadas
                },1000);
            }
        }
    }
//..................Fin Permisos-INICIO TOKENS
   public void obtenerToken() {

    FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    Context cont=getApplicationContext();
                    SharedPreferences sharprefs = cont.getSharedPreferences("ArchivoPOS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharprefs.edit();
                    editor.putString("CualEsMITOken",token);
                    editor.apply();
                    editor.commit();

                    // Log and toast

                    DatabaseReference direccion= FirebaseDatabase.getInstance().getReference("Direcciones/token");
                    direccion.push().setValue(token);

                }


            });
}



    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("canal1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}




        //...DEVUELVE UN StRING CON LAS FECHA Y HORA "2019/02/30 14:20"











