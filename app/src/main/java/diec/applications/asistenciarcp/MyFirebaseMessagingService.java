package diec.applications.asistenciarcp;

import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;





import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aplications.asistenciarcp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;




public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference();
        database.child("Posicion").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String time=dataSnapshot.child("hora").getValue(String.class);
                Double lati=dataSnapshot.child("latitud").getValue(Double.class);
                Double longi=dataSnapshot.child("longitud").getValue(Double.class);

                Context cont=getApplicationContext();
                SharedPreferences sharprefs = cont.getSharedPreferences("ArchivoPOS",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editData = sharprefs.edit();
                editData.putString("time",time);
                editData.putString("lati",lati.toString());
                editData.putString("longi",longi.toString());
                editData.apply();
                editData.commit(); }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ


        // Check if message contains a data payload.

        String remitente=" ";
        String yo=" ";
        if (remoteMessage.getData().size() > 0) {

            Context cont=getApplicationContext();
            SharedPreferences sharprefs = getSharedPreferences("ArchivoPOS",MODE_PRIVATE);

            yo=sharprefs.getString("CualEsMITOken","no hay nada!");
            remitente=remoteMessage.getData().get("direccion");
        }
        if (!remitente.equals(yo)){// solo envia la notificacion si el remitente no soy yo             ( para que funcione el condicional debe ser =>(!remitente.equals(yo))
        CrearNotificacion();
         }
    }

    public void CrearNotificacion(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, Mapa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"canal1")
                .setSmallIcon(R.drawable.marker_default)
                .setContentTitle("EMERGENCIA")
                .setContentText("Ubiquela en el mapa dentro de la APP")
                 .setVibrate(new long[] { 0, 1000, 1000, 1000, 1000 })
                 .setFullScreenIntent(pendingIntent,true);

            notificationManager.notify( 1,builder.build()); // el id sirve para identificarla, si tienen el mismo id se reemplanzan las notificaciones , es decir no se muestran 2 veces
    }
}
