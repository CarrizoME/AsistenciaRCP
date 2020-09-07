package diec.applications.asistenciarcp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aplications.asistenciarcp.R;

public class Configuracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
    }


    public void CambiarNumlocal(View coso){

        EditText num= (EditText) findViewById(R.id.RellenoNumLocal);
        String numero= num.getText().toString();

        Context cont=getApplicationContext();
        SharedPreferences sharprefs = cont.getSharedPreferences("ArchivoPOS",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharprefs.edit();

        edit.putString("NumeroLocal",numero);//Debe ir el 107 aca!
        edit.apply();
        edit.commit();

        Toast.makeText(this,"Su numero local cambio a Tel:"+numero,Toast.LENGTH_SHORT).show();

    }
    public void CambiarNumAlternativo(View coso){

        EditText num= (EditText) findViewById(R.id.RellenoNumAlternativo);
        String numero= num.getText().toString();

        Context cont=getApplicationContext();
        SharedPreferences sharprefs = cont.getSharedPreferences("ArchivoPOS",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharprefs.edit();

        edit.putString("NumeroAlternativo",numero);//Debe ir el 107 aca!
        edit.apply();
        edit.commit();

        Toast.makeText(this,"Su numero alternativo cambio a Tel:"+numero,Toast.LENGTH_SHORT).show();

    }
}
