package com.example.nutrihabit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nutrihabit2.alimentos.AlimentosCrearActivity;
import com.example.nutrihabit2.infoBasica.DatosBasicosActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class BienvenidaActivity extends AppCompatActivity {

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        getSupportActionBar().setTitle(R.string.Bienvenido);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.btnGanarMasa) :
                this.irADatosBasicos(1);
                break;
            case (R.id.btnPerderPeso) :
                this.irADatosBasicos(2);
                break;
            case (R.id.btnMantenerEstado) :
                this.irADatosBasicos(3);
                break;
        }
    }

    public void irADatosBasicos(int parSeleccion) {

        Log.d("TAG3", "NextClicked");
        this.guardarInformacion(parSeleccion);
        Intent intent = new Intent(this, DatosBasicosActivity.class);
        intent.putExtra("seleccion",parSeleccion);
        startActivity(intent);
    }

    // Crear el usuario en local si no existe
    private void guardarInformacion(int pSeleccion) {
        String id = this.getLocalUserId();
        if (id == null) {
            this.crearUsuarioFirebase(pSeleccion);
            // ToDo: crearUsuarioFirebase(User object);
        } else {
            this.actualizarSeleccion(pSeleccion,id);
        }
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
    private void crearUsuarioFirebase(int pProposito) {
        Map<String, Object> user = new HashMap<>();
        user.put("proposito", pProposito);

        DocumentReference userDocument = db.collection("users").document();
        userDocument.set(user);
        saveLocalUser(userDocument.getId());
    }

    private void actualizarSeleccion(int pProposito, String pUserId) {
        Map<String, Object> user = new HashMap<>();
        user.put("proposito", pProposito);

        DocumentReference userDocument = db.collection("users").document(pUserId);
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

}