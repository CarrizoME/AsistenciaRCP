package diec.applications.asistenciarcp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aplications.asistenciarcp.R;

public class LlamarAEmergencias extends AppCompatActivity {
    String Condicion="";
    String Mensaje="false";
    TextView NumLocal;
    TextView NumAlterntivo;
    String NumLoc;
    String NumAlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamar_aemergencias);
        CargarNumeros();
        TomarValores();
        if (Mensaje.equals("false") == Boolean.FALSE){
            AlertDialog.Builder Alerta= new AlertDialog.Builder(LlamarAEmergencias.this);
            Alerta.setMessage(Mensaje)
                    .setCancelable(true)
                    .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog titulo=Alerta.create();
            titulo.setTitle("");
            titulo.show();
        };


    }
    public void NumeroLocal(View view){
        Intent a=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+NumLoc));
        TomarValores();
        if(Condicion.equals("true")){
            Intent b=new Intent(this,EmergenciaASISTENCIaRCP.class);
            startActivity(b); }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(a);
    }
    public void NumeroAlternativo(View view){
        Intent a=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+NumAlt));

        TomarValores();
        if(Condicion.equals("true")){
            Intent b=new Intent(this,EmergenciaASISTENCIaRCP.class);
            startActivity(b); }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(a);
    }
    public void TomarValores(){

        Bundle extras= getIntent().getExtras();
        String intermedio=extras.getString("Alone");
        String mensaje=extras.getString("mensaje");
        Mensaje=mensaje;
        Condicion=intermedio;

    }
    public void CargarNumeros(){
        NumLocal= (TextView)findViewById(R.id.NumeroLocal);
        NumAlterntivo=(TextView)findViewById(R.id.NumeroAlternativo);

        Context cont=getApplicationContext();
        SharedPreferences sharprefs = cont.getSharedPreferences("ArchivoPOS",Context.MODE_PRIVATE);

        NumLoc =sharprefs.getString("NumeroLocal","107");
        NumAlt=sharprefs.getString("NumeroAlternativo","107");


        NumLocal.setText("Tel: "+NumLoc);
        NumAlterntivo.setText("Tel:"+NumAlt);

    }
}
