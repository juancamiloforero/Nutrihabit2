package com.example.nutrihabit2.infoBasica;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.accesoDatos.ConexionBD;
import com.example.nutrihabit2.menuPrincipal.menuPrincipal;
import com.example.nutrihabit2.modelos.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.example.nutrihabit2.menuPrincipal.menuPrincipal;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;


public class DatosBasicosActivity extends AppCompatActivity {

    EditText etEstatura;
    EditText etPeso;
    EditText etEdad;
    Spinner spGenero, spActividad, spObjetivo;
    LinearLayout llObjetivo;

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";


    int objetivo;
    boolean modoEdicion = false;
    Usuario usuarioActual;
    Button btSiguiente, btGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_basicos);
        getSupportActionBar().setTitle(R.string.datos_basicos);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.etEstatura = (EditText) findViewById(R.id.etEstatura);
        this.etPeso = (EditText) findViewById(R.id.etPeso);
        this.etEdad = (EditText) findViewById(R.id.etEdad);
        this.spGenero = (Spinner) findViewById(R.id.spGenero);
        this.spActividad = (Spinner) findViewById(R.id.spActividadFisica);
        this.spObjetivo = (Spinner) findViewById(R.id.spObjetivo);
        this.llObjetivo = (LinearLayout) findViewById(R.id.idLinearObjetivo);
        btSiguiente = findViewById(R.id.btSiguiente);
        btGuardar = findViewById(R.id.btGuardar);


        Intent intent = getIntent();
        this.objetivo = intent.getIntExtra("seleccion", 1);
        this.modoEdicion = intent.getBooleanExtra("editar", false);
        String genero = intent.getStringExtra("genero");
        String nivelActividad = intent.getStringExtra("nivelActividad");
        if (modoEdicion) {
            btGuardar.setVisibility(View.VISIBLE);
            btSiguiente.setVisibility(View.INVISIBLE);
            llObjetivo.setVisibility(View.VISIBLE);
            this.setSpinerGenero(genero);
            this.setSpinerNivelActividad(nivelActividad);
            this.spObjetivo.setSelection(this.objetivo - 1);

            /*
            this.etEstatura.setText(Float.toString(intent.getFloatExtra("estatura",0)));
            this.etPeso.setText(Float.toString(intent.getFloatExtra("peso",0)));
            this.etEdad.setText(Integer.toString(intent.getIntExtra("edad",0)));
             */
        } else {
            btGuardar.setVisibility(View.INVISIBLE);
            btSiguiente.setVisibility(View.VISIBLE);
            llObjetivo.setVisibility(View.INVISIBLE);
        }

    }

    private void setSpinerNivelActividad(String nivelActividad) {
        int resp = 0;
        switch (nivelActividad) {
            case "Moderado":
                resp = 1;
                break;
            case "Alto":
                resp = 2;
                break;
            case "Muy Alto":
                resp = 3;
                break;
        }
        this.spActividad.setSelection(resp);
    }

    private void setSpinerGenero(String genero) {
        int generoSelect = 0;
        if (genero.equals("Mujer")){
            generoSelect = 1;
        }
        this.spGenero.setSelection(generoSelect);
    }

    public void irAIMC(float estatura, float peso, int edad) {
        Intent intent = new Intent(this, ImcActivity.class);
        intent.putExtra("estatura", estatura);
        intent.putExtra("peso", peso);
        intent.putExtra("edad", edad);
        startActivity(intent);
        finish();
    }

    private String getStrGeneroGuardar(int pPosicion) {
        String resp = "Hombre";
        if (pPosicion == 1) {
            resp = "Mujer";
        }
        return resp;
    }

    private String getStrNivelActividadGuardar(int pPosicion) {
        String resp = "Bajo";
        switch (pPosicion) {
            case 1:
                resp = "Moderado";
                break;
            case 2:
                resp = "Alto";
                break;
            case 3:
                resp = "Muy Alto";
                break;
        }
        return resp;
    }

    public void onClick(View view) {
        //Captura de datos
        float estatura = Float.parseFloat(this.etEstatura.getText().toString());
        float peso = Float.parseFloat(this.etPeso.getText().toString());
        int edad = Integer.parseInt(this.etEdad.getText().toString());
        int nuevoObjetivo = this.spObjetivo.getSelectedItemPosition() + 1;

        String genero = this.getStrGeneroGuardar(this.spGenero.getSelectedItemPosition());
        String nivelActividad = this.getStrNivelActividadGuardar(this.spActividad.getSelectedItemPosition());

        this.guardarDatosBasicos(estatura, peso, edad, genero, nivelActividad, nuevoObjetivo);

        //this.irAIMC(estatura, peso, edad);

        switch (view.getId()) {
            case (R.id.btSiguiente):
                this.guardarDatosBasicos(estatura, peso, edad, genero, nivelActividad, this.objetivo);
                this.irAIMC(estatura, peso, edad);
                break;

            case (R.id.btGuardar):
                    this.guardarDatosBasicos(estatura, peso, edad, genero, nivelActividad, this.objetivo);
                break;
        }


    }

    private String getUserId() {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void guardarDatosBasicos(float pEstatura, float pPeso, int pEdad, String pGenero, String pNivelActividad, int pObjetivo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (this.getUserId() != null) {

            Map<String, Object> user = new HashMap<>();
            user.put("estatura", pEstatura);
            user.put("peso", pPeso);
            user.put("genero", pGenero);
            user.put("edad", pEdad);
            user.put("nivel_actividad", pNivelActividad);
            user.put("proposito", pObjetivo);

            // Add a new document with a generated ID
            db.collection("users").document(getUserId())
                    .set(user, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully written!");
                            finalizar();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error writing document", e);
                            finalizar();
                        }
                    });
        }
    }

    private void finalizar() {
        this.finish();
    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}