package diec.applications.asistenciarcp;



import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.aplications.asistenciarcp.R;


public class PantallaInicial extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        Context cont=getApplicationContext();
        SharedPreferences sharprefs =
                cont.getSharedPreferences("ArchivoPOS"
                        ,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharprefs.edit();

        if (sharprefs.getString("CargueTel","false")
                .equals("false")){
        edit.putString("NumeroLocal","107");//Debe ir el 107 aca!
        edit.putString("NumeroAlternativo","107");
        edit.putString("CargueTel","true");
        edit.apply();
        edit.commit();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(PantallaInicial.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }









    //ESTO SOLO SIRVE PARA ENVIAR NOTIFICACIONES A SI MISMO
    /*
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.CanalComunicacion);
            String description = getString(R.string.DescripcionCanal);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("jjjsw", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
