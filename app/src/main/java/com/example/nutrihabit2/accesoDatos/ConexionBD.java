package com.example.nutrihabit2.accesoDatos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.example.nutrihabit2.menuPrincipal.menuPrincipal;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ConexionBD {

    public ConexionBD() {

    }

    public void guardarDatosBasicos2(String userId,float pEstatura, float pPeso, int pEdad, String pGenero, String pNivelActividad, final boolean modoEdit, View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (userId != null) {

            Map<String, Object> user = new HashMap<>();
            user.put("estatura", pEstatura);
            user.put("peso", pPeso);
            user.put("genero", pGenero);
            user.put("edad", pEdad);
            user.put("nivel_actividad", pNivelActividad);

            // Add a new document with a generated ID
            db.collection("users").document(userId)
                    .set(user, SetOptions.merge())
            ;
        }
    }

}
