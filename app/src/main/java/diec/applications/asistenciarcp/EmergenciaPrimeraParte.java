package diec.applications.asistenciarcp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aplications.asistenciarcp.R;


public class EmergenciaPrimeraParte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia_primera_parte);

    }



    public void EstaConciente(View Coso){
        Intent otra= new Intent(this,LlamarAEmergencias.class);
        otra.putExtra("Alone","false");
        otra.putExtra("mensaje","No necesita asistencia RCP, llame a emergencias");

        startActivity(otra);

        }
    public void EstaInConciente(View Coso){
        Intent otra= new Intent(this,EmergenciaTercerParte.class);
        startActivity(otra);
    }




}
