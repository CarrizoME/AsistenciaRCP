package diec.applications.asistenciarcp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aplications.asistenciarcp.R;

public class EmergenciaTercerParte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia_tercer_parte);
    }
    public void Acompañado(View Coso){
        //Toast.makeText(this,"Comience con la asistencia RCP y que su compañero llame a emergencias",Toast.LENGTH_LONG).show();

        Intent otra= new Intent(this,EmergenciaASISTENCIaRCP.class);

        startActivity(otra);}

    public void Solo(View Coso){
        Intent otra= new Intent(this,LlamarAEmergencias.class);
        otra.putExtra("Alone","true");
        otra.putExtra("mensaje","Primero llame a emergencias y luego continue con la asistencia RCP");
        startActivity(otra);}
}
