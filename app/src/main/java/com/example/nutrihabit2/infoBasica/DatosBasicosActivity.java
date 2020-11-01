package com.example.nutrihabit2.infoBasica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class DatosBasicosActivity extends AppCompatActivity {

    EditText etEstatura;
    EditText etPeso;
    EditText etEdad;
    Spinner spGenero;
    Spinner spActividad;

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        btSiguiente = findViewById(R.id.btSiguiente);
        btGuardar = findViewById(R.id.btGuardar);

        Intent intent = getIntent();
        this.objetivo = intent.getIntExtra("seleccion", 0);
        this.modoEdicion = intent.getBooleanExtra("editar", false);
        if (modoEdicion) {
            btGuardar.setVisibility(View.VISIBLE);
            btSiguiente.setVisibility(View.INVISIBLE);
            /*
            this.etEstatura.setText(Float.toString(intent.getFloatExtra("estatura",0)));
            this.etPeso.setText(Float.toString(intent.getFloatExtra("peso",0)));
            this.etEdad.setText(Integer.toString(intent.getIntExtra("edad",0)));
            
             */
        } else {
            btGuardar.setVisibility(View.INVISIBLE);
            btSiguiente.setVisibility(View.VISIBLE);
        }

    }

    public void irAIMC(float estatura, float peso, int edad) {
        Intent intent = new Intent(this, ImcActivity.class);
        intent.putExtra("estatura",estatura);
        intent.putExtra("peso",peso);
        intent.putExtra("edad",edad);

        startActivity(intent);
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
        String genero = this.getStrGeneroGuardar(this.spGenero.getSelectedItemPosition());
        String nivelActividad = this.getStrNivelActividadGuardar(this.spActividad.getSelectedItemPosition());

        switch (view.getId()) {
            case (R.id.btSiguiente):
                this.guardarDatosBasicos(estatura,peso,edad,genero,nivelActividad);
                this.irAIMC(estatura,peso,edad);
                break;

            case (R.id.btGuardar):
                this.guardarDatosBasicos(estatura,peso,edad,genero,nivelActividad);
                //finish();
                onSupportNavigateUp();
        }
    }

    // Crear el usuario en local si no existe
    private void verifyUser() {
        String id = this.getLocalUserId();
        if (id == null) {
            //crearUsuarioFirebase();

            // ToDo: crearUsuarioFirebase(User object);
        } else {
            Log.d("ID_USER", "UserID: " + id);
        }
    }

    private void guardarEnFireBase(int pEstatura, int pPeso, String pGenero, int pEdad) {
        Map<String, Object> user = new HashMap<>();
        user.put("estatura", pEstatura);
        user.put("peso", pPeso);
        user.put("genero", pGenero);
        user.put("edad", pEdad);

        DocumentReference userDocument = db.collection("users").document();
        userDocument.set(user, SetOptions.merge());
        saveLocalUser(userDocument.getId());
    }
    private void saveLocalUser(String id) {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(this.keyUserId, id);
        editor.apply();
        Log.d("LOCAL_USER_CREATED", "Se creó el usuario con el ID: " + id);
    }


    // Retorna el id del usuario guardado en local, si no existe retorna null
    public String getLocalUserId() {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        String defaultID = getResources().getString(R.string.defaultUserId);
        String userID = sharedPref.getString(this.keyUserId, defaultID);

        if (!userID.equals(defaultID)) {
            return userID;
        } else {
            return null;
        }
    }

    private String getUserId() {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void guardarDatosBasicos(float pEstatura, float pPeso, int pEdad, String pGenero, String pNivelActividad) {
        if (this.getUserId() != null) {

            Map<String, Object> user = new HashMap<>();
            user.put("estatura", pEstatura);
            user.put("peso", pPeso);
            user.put("genero", pGenero);
            user.put("edad", pEdad);
            user.put("nivel_actividad", pNivelActividad);

            // Add a new document with a generated ID
            db.collection("users").document(getUserId())
                    .set(user, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error writing document", e);
                        }
                    });
        }
    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}